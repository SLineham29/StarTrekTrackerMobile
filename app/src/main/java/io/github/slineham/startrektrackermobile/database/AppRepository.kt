package io.github.slineham.startrektrackermobile.database

import io.github.slineham.startrektrackermobile.api.EpisodeDetails
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val seriesDao: SeriesDao,
    private val seasonsDao: SeasonsDao,
    private val episodesDao: EpisodesDao
) {

    suspend fun addSeriesToDb(series: Series) {
        seriesDao.insertSeries(series)
    }

    suspend fun getSeriesFromDb(id: Int): Series? {
        val series = seriesDao.getSeriesById(id)
        return series
    }

    suspend fun getSeasonFromDb(combinedId: String): Seasons? {
        val season = seasonsDao.getSeasonById(combinedId)
        return season
    }

    suspend fun getEpisodeFromDb(combinedId: String): Episodes {
        val episode = episodesDao.getEpisodeById(combinedId)
        return episode
    }

    suspend fun addSeasonToDb(season: Seasons) {
        seasonsDao.insertSeason(season)
    }

    suspend fun addEpisodesToDb(episodes: List<EpisodeDetails>, seriesId: Int, seasonNum: Int) {
        for(episode in episodes) {
            val newEpisode = Episodes(
                "${seriesId}_${seasonNum}_${episode.episodeNumber}",
                seriesId,
                seasonNum,
                false,
                episode
            )
            episodesDao.insertEpisode(newEpisode)
        }
    }

    suspend fun updateWatchedEpisode(combinedId: String, haveWatched: Boolean) {
        episodesDao.updateEpisodeWatchedStatus(combinedId, haveWatched)

        val extractedId = combinedId.substringBefore("_").toInt()

        if(haveWatched) {
            seriesDao.increaseTotalEpisodesWatched(extractedId)
        } else {
            seriesDao.decreaseTotalEpisodesWatched(extractedId)
        }
    }
}