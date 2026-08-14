package io.github.slineham.startrektrackermobile.database

import androidx.room3.Embedded
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import io.github.slineham.startrektrackermobile.api.EpisodeDetails


@Entity("episodes")
data class Episodes(
    @PrimaryKey
    val combinedId: String,

    val seriesId: Int,
    val seasonNum: Int,
    val hasWatched: Boolean,

    @Embedded
    val details: EpisodeDetails
)