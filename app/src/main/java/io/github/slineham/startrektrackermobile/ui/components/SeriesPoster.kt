package io.github.slineham.startrektrackermobile.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun SeriesPoster(posterPath: String, posterSize: Int, modifier: Modifier) {

    AsyncImage(
        model = "https://image.tmdb.org/t/p/w$posterSize$posterPath",
        contentDescription = "Series Poster",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    )
}