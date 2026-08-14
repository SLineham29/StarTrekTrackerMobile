package io.github.slineham.startrektrackermobile.api
import androidx.room3.Ignore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDetails (
    @Ignore val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("last_air_date")
    val lastAirDate: String = "",
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int = 0,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int = 0,
    @SerialName("poster_path")
    val posterPath: String = ""
) {


}

@Serializable
data class Images(
    val logos: List<Logo> = emptyList()
)

@Serializable
data class Logo(
    @SerialName("aspect_ratio")
    val aspectRatio: Double = 0.000,
    val height: Int = 0,
    val width: Int = 0,
    @SerialName("iso_639_1")
    val language: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.000,
    @SerialName("file_path")
    val filePath: String = ""
)