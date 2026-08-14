package io.github.slineham.startrektrackermobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.slineham.startrektrackermobile.BuildConfig
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

    private val api = TmdbApi().service;

    var series = MutableStateFlow<List<Series>>(emptyList())

    var cachedSeasons = MutableStateFlow<Map<Pair<Int, Int>, SeasonDetails>>(emptyMap())

    private val _chosenSeries = MutableStateFlow<Series?>(null)
    val chosenSeries: StateFlow<Series?> = _chosenSeries.asStateFlow()

    private val _chosenSeriesSeason = MutableStateFlow<Seasons?>(null)
    val chosenSeriesSeason: StateFlow<Seasons?> = _chosenSeriesSeason.asStateFlow()

    private val _chosenEpisodeDetails = MutableStateFlow<Episodes?>(null)
    val chosenEpisodeDetails: StateFlow<Episodes?> = _chosenEpisodeDetails.asStateFlow()

    init {
        getSeries()
    }

    private fun getSeasonEpisodeNames(season: SeasonDetails): List<String> {
        val episodeNames: MutableList<String> = mutableListOf()
        for(episode in season.episodes) {
            episodeNames += episode.name
        }
        return episodeNames
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

    fun getChosenSeries(seriesId: Int) {
        viewModelScope.launch {
            val dbSeries = appRepository.getSeriesFromDb(seriesId)
            _chosenSeries.value = dbSeries
        }
    }

    fun getChosenSeriesSeason(seriesId: Int, seasonNum: Int){
        viewModelScope.launch {
            val combinedId = "${seriesId}_${seasonNum}"

            var dbSeason = appRepository.getSeasonFromDb(combinedId)

            dbSeason ?:
            try {
                val seriesSeason = api.getSeasonDetails(seriesId, seasonNum, BuildConfig.TMDB_API_KEY)

                dbSeason = Seasons(combinedId, seriesId, seasonNum, seriesSeason.episodes.count(), getSeasonEpisodeNames(seriesSeason), seriesSeason.overview)
                appRepository.addSeasonToDb(dbSeason)
                appRepository.addEpisodesToDb(seriesSeason.episodes, seriesId, seasonNum)
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

    fun updateEpisodeWatchedStatus(combinedId: String, hasWatched: Boolean) {
        viewModelScope.launch {
            appRepository.updateWatchedEpisode(combinedId, hasWatched)
            _chosenEpisodeDetails.value = _chosenEpisodeDetails.value?.copy(hasWatched = hasWatched)
        }
    }

    fun getNumOfSeasons(seriesId: Int): Int {
        val series = series.value.find {it.seriesId == seriesId}
        return series?.details?.numberOfSeasons ?: 1
    }
}