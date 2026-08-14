package io.github.slineham.startrektrackermobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.slineham.startrektrackermobile.ui.components.SeriesPoster
import io.github.slineham.startrektrackermobile.ui.components.SeriesProgress
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun MainMenuScreen(viewModel: TrackerViewModel = viewModel(), navController: NavController) {

    val series by viewModel.series.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF05070C))
    ) {
        if(series.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }
        } else {

            val pagerState = rememberPagerState(pageCount = {series.size})

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "STAR TREK TRACKER",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 40.sp,
                    color = LcarsColours.Orange,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) { page ->
                    val series = series[page]

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth(0.82f)
                                .wrapContentHeight()
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
                                .clickable {
                                    navController.navigate("seriesDetails/${series.seriesId}")
                                }
                        ) {
                            Column() {
                                SeriesPoster(
                                    series.details.posterPath, 780, Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(2f / 3f)
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(LcarsColours.Orange)
                                        .padding(vertical = 10.dp, horizontal = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    SeriesProgress(
                                        totalEpisodesWatched = series.totalEpisodesWatched,
                                        totalEpisodes = series.details.numberOfEpisodes,
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .padding(3.dp)
                                            .height(10.dp),
                                        colour = LcarsColours.Violet,
                                        trackColour = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .background(
                            color = Color(0xFF1A1A1A),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = LcarsColours.Violet,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 15.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "SERIES ${pagerState.currentPage + 1} / ${pagerState.pageCount}",
                        fontFamily = AntonioFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = LcarsColours.Violet,
                        letterSpacing = 1.5.sp
                    )
                }
            }
        }
    }
}