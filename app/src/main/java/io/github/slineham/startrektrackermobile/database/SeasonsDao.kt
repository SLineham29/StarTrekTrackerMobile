package io.github.slineham.startrektrackermobile.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface SeasonsDao {

    @Query("SELECT * FROM seasons WHERE combinedId = :id")
    suspend fun getSeasonById(id: String): Seasons?

    @Insert()
    suspend fun insertSeason(season: Seasons)

    @Update
    suspend fun updateSeason(season: Seasons)
}