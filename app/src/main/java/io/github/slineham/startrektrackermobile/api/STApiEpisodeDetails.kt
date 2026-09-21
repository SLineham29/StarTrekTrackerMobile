package io.github.slineham.startrektrackermobile.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeBaseResponse(
    val page: STApiPage? = null,
    val episodes: List<STApiEpisodeDetails> = emptyList()
)

@Serializable
data class STApiPage(
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Int = 0,
    val totalPages: Int = 0
)

@Serializable
data class STApiEpisodeDetails (
    val title: String = "",
    @SerialName("stardateFrom")
    val stardate: Double? = null,
    val seasonNumber: Int = 0,
    @SerialName("episodeNumber")
    val productionEpisodeNumber: Int = 0,
    val usAirDate: String = ""
    ) {
}