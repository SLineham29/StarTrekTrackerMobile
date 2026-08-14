package io.github.slineham.startrektrackermobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import io.github.slineham.startrektrackermobile.ui.components.EpisodeGuestStars
import io.github.slineham.startrektrackermobile.ui.components.EpisodeSelector
import io.github.slineham.startrektrackermobile.ui.components.SeasonSelector
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun EpisodeDetailsScreen(
    viewModel: TrackerViewModel = viewModel(),
    seasonNum: Int,
    seriesId: Int,
    episodeNum: Int
) {

    val chosenSeries by viewModel.chosenSeries.collectAsState()

    val chosenSeason by viewModel.chosenSeriesSeason.collectAsState()

    val chosenEpisode by viewModel.chosenEpisodeDetails.collectAsState()

    var episodeNum by remember { mutableIntStateOf(episodeNum) }

    var currentSeason by remember {mutableIntStateOf(seasonNum)}

    LaunchedEffect(episodeNum, chosenSeason) {
        viewModel.getChosenEpisode(seriesId, chosenSeason?.seasonNum ?: 1, episodeNum)
    }

    LaunchedEffect(seriesId, currentSeason) {
        viewModel.getChosenSeriesSeason(seriesId, currentSeason)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05070C))
    ) {
        if (chosenSeason == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }
        }
        else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111111)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LcarsColours.LightBlue.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                ) {
                    Column() {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = LcarsColours.LightBlue,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                        ) {
                            Text(
                                text = "SEASON/EPISODE SELECTOR //",
                                fontFamily = AntonioFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SeasonSelector(
                                chosenSeries?.details?.numberOfSeasons ?: 1,
                                currentSeason,
                                onSeasonSelected = { newSeasonNum ->
                                    currentSeason = newSeasonNum
                                    episodeNum = 1
                                })

                            EpisodeSelector(
                                chosenSeason?.numOfEpisodes ?: 1,
                                chosenSeason?.episodeNames ?: emptyList(),
                                episodeNum,
                                onEpisodeSelected = { newEpisodeNum -> episodeNum = newEpisodeNum
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = chosenEpisode?.details?.name ?: "ERROR OBTAINING EPISODE",
                    textAlign = TextAlign.Center,
                    fontFamily = AntonioFontFamily,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = LcarsColours.Orange,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Box(modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = (LcarsColours.Orange.copy(alpha = 0.7f)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))

                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original${chosenEpisode?.details?.stillPath}",
                        contentDescription = "Episode Still",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF111111), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFF222222), RoundedCornerShape(12.dp))
                        .padding(vertical = 5.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Overall User Rating",
                            tint = LcarsColours.Orange,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "RATING // ${chosenEpisode?.details?.voteAverage ?: 0.0} / 10",
                            fontFamily = AntonioFontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = LcarsColours.TextPrimary,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Air Date",
                            tint = LcarsColours.Violet,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AIR DATE // ${chosenEpisode?.details?.airDate ?: "Unknown"}",
                            fontFamily = AntonioFontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = LcarsColours.Violet,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (chosenEpisode?.hasWatched == true) LcarsColours.LightBlue.copy(alpha = 0.15f) else Color(0xFF111111),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (chosenEpisode?.hasWatched == true) LcarsColours.LightBlue else Color(0xFF222222),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (chosenEpisode?.hasWatched == true) "STATUS // LOGGED AS WATCHED" else "STATUS // UNWATCHED",
                        fontFamily = AntonioFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = if (chosenEpisode?.hasWatched == true) LcarsColours.LightBlue else LcarsColours.TextSecondary,
                        letterSpacing = 1.sp
                    )
                    Checkbox(
                        checked = chosenEpisode?.hasWatched ?: false,
                        onCheckedChange = { checked -> viewModel.updateEpisodeWatchedStatus(
                            "${seriesId}_${seasonNum}_${episodeNum}",
                            checked
                        )},
                        colors = CheckboxDefaults.colors(
                            checkedColor = LcarsColours.LightBlue,
                            checkmarkColor = Color.Black,
                            uncheckedColor = LcarsColours.TextSecondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF111111), shape = RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFF222222), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "EPISODE OVERVIEW //",
                        fontFamily = AntonioFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = LcarsColours.Violet,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Text(
                        text = chosenEpisode?.details?.overview ?: "NO OVERVIEW AVAILABLE.",
                        fontFamily = AntonioFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = LcarsColours.TextSecondary,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                EpisodeGuestStars(chosenEpisode?.details?.guestStars ?: emptyList())
            }
        }
    }
}