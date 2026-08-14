package io.github.slineham.startrektrackermobile.database

import androidx.room3.ColumnTypeConverter
import io.github.slineham.startrektrackermobile.api.GuestStar
import kotlinx.serialization.json.Json

class ClassConverters {

    @ColumnTypeConverter
    fun guestStarsToJson(guestStars: List<GuestStar>): String {
        return Json.encodeToString(guestStars)
    }

    @ColumnTypeConverter
    fun guestStarsFromJson(guestStars: String): List<GuestStar> {
        return if(guestStars.isBlank()) emptyList() else Json.Default.decodeFromString(guestStars)
    }

    @ColumnTypeConverter
    fun episodeNamesToJson(episodeNames: List<String>): String {
        return Json.encodeToString(episodeNames)
    }

    @ColumnTypeConverter
    fun episodeNamesFromJson(episodeNames: String): List<String> {
        return if(episodeNames.isBlank()) emptyList() else Json.Default.decodeFromString(episodeNames)
    }
}