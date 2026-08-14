package io.github.slineham.startrektrackermobile.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonDetails(
    val seriesId: Int = 0,
    @SerialName("season_number")
    val seasonNumber: Int = 0,
    val overview: String,
    val episodes: List<EpisodeDetails> = emptyList()
)

@Serializable
data class EpisodeDetails(
    @SerialName("air_date")
    val airDate: String = "",
    @SerialName("episode_number")
    val episodeNumber: Int = 0,
    val name: String = "",
    val overview: String = "",
    val runtime: Int = 0,
    @SerialName("still_path")
    val stillPath: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("guest_stars")
    val guestStars: List<GuestStar> = emptyList()
)

@Serializable
data class GuestStar(
    val character: String = "",
    val name: String = "",
    @SerialName("profile_path")
    val profilePath: String? = ""
)