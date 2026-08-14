package io.github.slineham.startrektrackermobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import io.github.slineham.startrektrackermobile.ui.components.SeasonSelector
import io.github.slineham.startrektrackermobile.ui.components.SeriesPoster
import io.github.slineham.startrektrackermobile.ui.components.SeriesProgress
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun SeriesDetailsScreen(
    viewModel: TrackerViewModel = viewModel(),
    navController: NavController,
    seriesId: Int
) {

    val chosenSeries by viewModel.chosenSeries.collectAsStateWithLifecycle()

    val chosenSeason by viewModel.chosenSeriesSeason.collectAsStateWithLifecycle()

    var seasonNum by remember { mutableIntStateOf(chosenSeason?.seasonNum ?: 1) }

    LaunchedEffect(seasonNum) {
        viewModel.getChosenSeriesSeason(seriesId, seasonNum)
    }

    LaunchedEffect(seriesId) {
        viewModel.getChosenSeries(seriesId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF05070C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(20.dp),
                        spotColor = LcarsColours.Orange,
                        ambientColor = LcarsColours.Orange
                    )
                    .border(
                        width = 1.5.dp,
                        color = LcarsColours.Orange.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
            ) {
                SeriesPoster(
                    posterPath = chosenSeries?.details?.posterPath ?: "",
                    posterSize = 342,
                    modifier = Modifier
                        .height(240.dp)
                        .aspectRatio(2f / 3f)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(
                        color = Color(0xFF111111),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = LcarsColours.LightBlue.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { navController.navigate("seriesDetails/${seriesId}/watched")}
            ) {
                Text(
                    text = "PROGRESS // ",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = LcarsColours.LightBlue,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${chosenSeries?.totalEpisodesWatched ?: 0} / ${chosenSeries?.details?.numberOfEpisodes ?: 1} EPISODES",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = LcarsColours.TextPrimary
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "View watched episodes",
                    tint = LcarsColours.LightBlue,
                    modifier = Modifier.size(13.dp)
                )
            }

            SeriesProgress(
                totalEpisodesWatched = chosenSeries?.totalEpisodesWatched ?: 0,
                totalEpisodes = chosenSeries?.details?.numberOfEpisodes ?: 1,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 15.dp),
                colour = LcarsColours.Violet,
                trackColour = LcarsColours.Orange
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Air Dates",
                    tint = LcarsColours.Violet,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${chosenSeries?.details?.firstAirDate ?: ""} - ${chosenSeries?.details?.lastAirDate ?: ""}",
                    fontFamily = AntonioFontFamily,
                    fontSize = 14.sp,
                    color = LcarsColours.Violet,
                    letterSpacing = 1.sp
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111111), shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFF222222), RoundedCornerShape(16.dp))
                .padding(15.dp)
            ) {
                Text(
                    text = "SERIES OVERVIEW //",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = LcarsColours.Orange,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = chosenSeries?.details?.overview ?: "COULDN'T OBTAIN SERIES OVERVIEW",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = LcarsColours.TextSecondary,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

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
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                    ) {
                        Text(
                            text = "SEASON SELECTOR //",
                            fontFamily = AntonioFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.Black,
                            letterSpacing = 1.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SeasonSelector(
                            chosenSeries?.details?.numberOfSeasons ?: 1,
                            seasonNum,
                            onSeasonSelected = {newSeasonNum -> seasonNum = newSeasonNum})
                        Button(
                            onClick = { navController.navigate("seriesDetails/${seriesId}/$seasonNum") },
                            colors = ButtonDefaults.buttonColors(containerColor = LcarsColours.Orange),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "VIEW SEASON DETAILS",
                                fontFamily = AntonioFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 15.sp)
                        }
                    }

                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                        HorizontalDivider(color = Color(0xFF222222))
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "SEASON $seasonNum OVERVIEW //",
                            fontFamily = AntonioFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = LcarsColours.Violet,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        if(chosenSeason?.overview.isNullOrEmpty()) {
                            Text(
                                text = "NO OVERVIEW AVAILABLE",
                                textAlign = TextAlign.Center,
                                fontFamily = AntonioFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp,
                                color = LcarsColours.TextSecondary,
                                lineHeight = 20.sp
                            )
                        } else {
                            Text(
                                text = chosenSeason?.overview ?: "NO OVERVIEW AVAILABLE.",
                                fontFamily = AntonioFontFamily,
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp,
                                color = LcarsColours.TextSecondary,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}