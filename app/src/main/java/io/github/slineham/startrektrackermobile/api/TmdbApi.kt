package io.github.slineham.startrektrackermobile.api

import io.github.slineham.startrektrackermobile.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlinx.serialization.json.Json
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface TmdbApiService {

    @GET("tv/{series_id}")
    suspend fun getSeriesDetails(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): SeriesDetails

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("series_id") seriesId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): SeasonDetails

    @GET("tv/{series_id}/images")
    suspend fun getSeriesImages(
        @Path("series_id") seriesId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Images
}

class TmdbApi {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service: TmdbApiService = retrofit.create(TmdbApiService::class.java)
}