package io.github.slineham.startrektrackermobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun EpisodesWatchedScreen(viewModel: TrackerViewModel = viewModel(), seriesId: Int) {

    val chosenSeries by viewModel.chosenSeries.collectAsStateWithLifecycle()

    var numOfSeasons by remember { mutableIntStateOf(chosenSeries?.details?.numberOfSeasons ?: 1) }

    val watchedEpisodes by viewModel.seriesWatchedEpisodes.collectAsStateWithLifecycle()

    val seasonEpisodesList = remember(watchedEpisodes) {
        watchedEpisodes.groupBy { it.seasonNum }
    }

    LaunchedEffect(seriesId) {
        viewModel.getSeriesWatchedEpisodes(seriesId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF05070C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if(chosenSeries?.logoPath.isNullOrEmpty()) {
                Text(
                    text = chosenSeries?.details?.name ?: "Couldn't get series name.",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = LcarsColours.Orange,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
            }
            else {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${chosenSeries!!.logoPath}",
                    contentDescription = "Series Logo"
                )
            }

            Text(
                text = "WATCHED EPISODES",
                fontFamily = AntonioFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = LcarsColours.Orange,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(vertical = 10.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111111)),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, LcarsColours.LightBlue.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                (1..numOfSeasons).forEach { season ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, LcarsColours.Violet.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = LcarsColours.Violet,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 6.dp, horizontal = 12.dp)
                        ) {
                            Row() {
                                Text(
                                    text = "SEASON $season // ",
                                    fontFamily = AntonioFontFamily,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp,
                                    color = Color.Black,
                                    letterSpacing = 1.sp
                                )

                                Text(
                                    text = "${seasonEpisodesList[season]?.size ?: 0} Episodes",
                                    fontFamily = AntonioFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                )
                            }
                        }

                        (seasonEpisodesList[season])?.forEach { episode ->
                            Text(
                                text = "Episode ${episode.details.episodeNumber} - ${episode.details.name}",
                                fontFamily = AntonioFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = LcarsColours.TextSecondary,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                            )
                            HorizontalDivider(color = Color(0xFF222222))
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}