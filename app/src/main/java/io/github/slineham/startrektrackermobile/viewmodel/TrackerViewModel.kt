package io.github.slineham.startrektrackermobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.slineham.startrektrackermobile.BuildConfig
import io.github.slineham.startrektrackermobile.api.EpisodeDetails
import io.github.slineham.startrektrackermobile.api.STApi
import io.github.slineham.startrektrackermobile.api.SeasonDetails
import io.github.slineham.startrektrackermobile.api.SeriesIDs
import io.github.slineham.startrektrackermobile.api.TmdbApi
import io.github.slineham.startrektrackermobile.api.chooseBestLogo
import io.github.slineham.startrektrackermobile.database.AppRepository
import io.github.slineham.startrektrackermobile.database.Episodes
import io.github.slineham.startrektrackermobile.database.Seasons
import io.github.slineham.startrektrackermobile.database.Series
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val api = TmdbApi().service
    private val stApi = STApi().service

    var series = MutableStateFlow<List<Series>>(emptyList())

    private val _chosenSeries = MutableStateFlow<Series?>(null)
    val chosenSeries: StateFlow<Series?> = _chosenSeries.asStateFlow()

    private val _chosenSeriesSeason = MutableStateFlow<Seasons?>(null)
    val chosenSeriesSeason: StateFlow<Seasons?> = _chosenSeriesSeason.asStateFlow()

    private val _chosenEpisodeDetails = MutableStateFlow<Episodes?>(null)
    val chosenEpisodeDetails: StateFlow<Episodes?> = _chosenEpisodeDetails.asStateFlow()

    private val _seriesWatchedEpisodes = MutableStateFlow<List<Episodes>>(emptyList())
    val seriesWatchedEpisodes: StateFlow<List<Episodes>> = _seriesWatchedEpisodes.asStateFlow()

    private val _episodeNameList = MutableStateFlow<List<Pair<Int, String>>>(emptyList())
    val episodeNameList: StateFlow<List<Pair<Int, String>>> = _episodeNameList.asStateFlow()

    init {
        getSeries()
    }

    private fun getSeries() {
        viewModelScope.launch {
            println("Getting Series Details...")
            val seriesList = SeriesIDs.seriesIDs.mapNotNull { id ->

                val dbSeries = appRepository.getSeriesFromDb(id)

                dbSeries ?:
                try {
                    val details = api.getSeriesDetails(id, BuildConfig.TMDB_API_KEY)

                    val seriesImages = api.getSeriesImages(id, BuildConfig.TMDB_API_KEY)

                    val bestLogoPath = chooseBestLogo(seriesImages.logos)

                    val newDbSeries = Series(details.id, 0, bestLogoPath, details)
                    appRepository.addSeriesToDb(newDbSeries)
                    newDbSeries
                } catch (e: Exception) {
                    println("TMDB_LOG: Failed ID $id -> ${e.localizedMessage}")
                    null
                }
            }
            series.value = seriesList
        }
    }

    private fun getSeasonEpisodeNames(season: SeasonDetails): List<String> {
        val episodeNames: MutableList<String> = mutableListOf()
        for(episode in season.episodes) {
            episodeNames += episode.name
        }
        return episodeNames
    }

    fun getSeasonEpisodeNames(seriesId: Int, seasonNum: Int, inProdOrder: Boolean) {
        viewModelScope.launch {
            val episodes: List<Pair<Int, String>> = if(inProdOrder) {
                appRepository.getProdOrderEpisodeNamesFromDb(seriesId, seasonNum)
            } else {
                appRepository.getEpisodeNamesFromDb(seriesId, seasonNum)
            }
            _episodeNameList.value = episodes
        }
    }

    fun getChosenSeries(seriesId: Int) {
        viewModelScope.launch {
            val dbSeries = appRepository.getSeriesFromDb(seriesId)
            _chosenSeries.value = dbSeries
        }
    }

    suspend fun getExtraEpisodeDetails(episodeDetails: EpisodeDetails): EpisodeDetails {
        val episodeExtrasResponse = stApi.getEpisodeDetails(
            episodeDetails.name,
            episodeDetails.airDate
        )
        Log.i("STApi", episodeExtrasResponse.toString())
        if (episodeExtrasResponse.episodes.isEmpty()) {
            return episodeDetails
        }
        val episodeExtras = episodeExtrasResponse.episodes[0]
        print(episodeExtras)
        if(episodeExtras.title == episodeDetails.name && episodeExtras.usAirDate == episodeDetails.airDate) {
            episodeDetails.stardate = episodeExtras.stardate
            episodeDetails.productionEpisodeNumber = episodeExtras.productionEpisodeNumber
        }
        return episodeDetails
    }

    fun getChosenSeriesSeason(seriesId: Int, seasonNum: Int){
        viewModelScope.launch {
            val combinedId = "${seriesId}_${seasonNum}"

            var dbSeason = appRepository.getSeasonFromDb(combinedId)

            dbSeason ?:
            try {
                val seriesSeason = api.getSeasonDetails(seriesId, seasonNum, BuildConfig.TMDB_API_KEY)

                val updatedEpisodeDetails = seriesSeason.episodes.map { episode ->
                    getExtraEpisodeDetails(episode)
                }

                dbSeason = Seasons(combinedId, seriesId, seasonNum, seriesSeason.episodes.count(), getSeasonEpisodeNames(seriesSeason), seriesSeason.overview)
                appRepository.addSeasonToDb(dbSeason)
                appRepository.addEpisodesToDb(updatedEpisodeDetails, seriesId, seasonNum)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
            _chosenSeriesSeason.value = dbSeason
        }
    }

    fun getChosenEpisode(seriesId: Int, seasonNum: Int, episodeNum: Int) {
        viewModelScope.launch {
            val combinedId = "${seriesId}_${seasonNum}_${episodeNum}"

            val dbEpisode = appRepository.getEpisodeFromDb(combinedId)

            _chosenEpisodeDetails.value = dbEpisode
        }
    }

    fun getChosenProductionOrderEpisode(seriesId: Int, seasonNum: Int, prodEpisodeNum: Int) {
        viewModelScope.launch {
            val dbEpisode = appRepository.getProdOrderEpisodeFromDb(seriesId, seasonNum, prodEpisodeNum)

            _chosenEpisodeDetails.value = dbEpisode
        }
    }

    fun updateEpisodeWatchedStatus(combinedId: String, hasWatched: Boolean) {
        viewModelScope.launch {
            appRepository.updateWatchedEpisode(combinedId, hasWatched)
            _chosenEpisodeDetails.value = _chosenEpisodeDetails.value?.copy(hasWatched = hasWatched)
        }
    }

    fun getSeriesWatchedEpisodes(seriesId: Int) {
        viewModelScope.launch {
            val watchedEpisodes = appRepository.getWatchedEpisodes(seriesId)
            _seriesWatchedEpisodes.value = watchedEpisodes
        }
    }
}