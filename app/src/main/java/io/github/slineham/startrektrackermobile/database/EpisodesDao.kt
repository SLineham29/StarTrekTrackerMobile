package io.github.slineham.startrektrackermobile.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes WHERE combinedId = :id")
    suspend fun getEpisodeById(id: String): Episodes

    @Query("SELECT * FROM episodes WHERE seriesId = :id AND seasonNum = :seasonNum AND productionEpisodeNumber = :prodEpNum")
    suspend fun getProdOrderEpisode(id: Int, seasonNum: Int, prodEpNum: Int): Episodes

    @Query("SELECT productionEpisodeNumber, name FROM episodes WHERE seriesId = :id AND seasonNum = :seasonNum ORDER BY productionEpisodeNumber ASC")
    suspend fun getProdOrderEpisodeNames(id: Int, seasonNum: Int): List<Pair<Int, String>>

    @Query("SELECT episodeNumber, name FROM episodes WHERE seriesId = :id AND seasonNum = :seasonNum ORDER BY episodeNumber ASC")
    suspend fun getEpisodeNames(id: Int, seasonNum: Int): List<Pair<Int, String>>

    @Insert()
    suspend fun insertEpisode(episode: Episodes)

    @Update
    suspend fun updateEpisode(episode: Episodes)

    @Query("UPDATE episodes SET hasWatched = :watchedStatus WHERE combinedId = :id")
    suspend fun updateEpisodeWatchedStatus(id: String, watchedStatus: Boolean)

    @Query("SELECT * FROM episodes WHERE seriesId = :seriesId AND hasWatched = TRUE")
    suspend fun getAllWatchedEpisodes(seriesId: Int): List<Episodes>
}