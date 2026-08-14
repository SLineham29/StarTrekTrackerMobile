package io.github.slineham.startrektrackermobile.api

fun chooseBestLogo(logos: List<Logo>): String {
    val bestLogo = logos.filter { it.language == "en" }
        .maxByOrNull { it.voteAverage }

    return bestLogo?.filePath ?: ""
}