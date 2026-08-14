package io.github.slineham.startrektrackermobile.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.ColumnTypeConverters

@Database(entities = [Series::class, Seasons::class, Episodes::class],version = 1)
@ColumnTypeConverters(ClassConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun seriesDao(): SeriesDao
    abstract fun seasonsDao(): SeasonsDao
    abstract fun episodesDao(): EpisodesDao
}