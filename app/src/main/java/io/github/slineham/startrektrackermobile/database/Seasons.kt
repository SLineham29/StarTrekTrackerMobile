package io.github.slineham.startrektrackermobile.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity("seasons")
class Seasons (
    @PrimaryKey
    val combinedId: String,

    val seriesId: Int,
    val seasonNum: Int,
    val numOfEpisodes: Int,
    val episodeNames: List<String>,
    val overview: String
)