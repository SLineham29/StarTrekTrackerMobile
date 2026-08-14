package io.github.slineham.startrektrackermobile.database

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import io.github.slineham.startrektrackermobile.api.SeriesDetails

@Entity("series")
class Series (
    @PrimaryKey
    val seriesId: Int,

    val totalEpisodesWatched: Int,

    val logoPath: String,

    @Embedded
    val details: SeriesDetails
)