package io.github.slineham.startrektrackermobile.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface SeriesDao {

    @Query("SELECT * FROM series WHERE seriesId = :id")
    suspend fun getSeriesById(id: Int): Series?

    @Query("SELECT totalEpisodesWatched FROM series WHERE seriesId = :id")
    suspend fun getTotalEpisodesWatched(id: Int): Int

    @Insert()
    suspend fun insertSeries(series: Series)

    @Update
    suspend fun updateSeries(series: Series)

    @Query("UPDATE series SET totalEpisodesWatched = totalEpisodesWatched + 1 WHERE seriesId = :id")
    suspend fun increaseTotalEpisodesWatched(id: Int)

    @Query("UPDATE series SET totalEpisodesWatched = MAX(0, totalEpisodesWatched - 1) WHERE seriesId = :id")
    suspend fun decreaseTotalEpisodesWatched(id: Int)
}