package io.github.slineham.startrektrackermobile.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes WHERE combinedId = :id")
    suspend fun getEpisodeById(id: String): Episodes

    @Insert()
    suspend fun insertEpisode(episode: Episodes)

    @Update
    suspend fun updateEpisode(episode: Episodes)

    @Query("UPDATE episodes SET hasWatched = :watchedStatus WHERE combinedId = :id")
    suspend fun updateEpisodeWatchedStatus(id: String, watchedStatus: Boolean)
}