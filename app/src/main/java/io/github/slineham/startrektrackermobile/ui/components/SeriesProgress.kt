package io.github.slineham.startrektrackermobile.ui.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun SeriesProgress(
    totalEpisodesWatched: Int,
    totalEpisodes: Int,
    modifier: Modifier = Modifier,
    colour: Color,
    trackColour: Color
) {
    val progress = totalEpisodesWatched.toFloat() / totalEpisodes.toFloat()

    LinearProgressIndicator(
        progress = {progress},
        modifier = modifier,
        color = colour,
        trackColor = trackColour,
        strokeCap = StrokeCap.Round,
        drawStopIndicator = {}
    )
}