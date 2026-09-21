package io.github.slineham.startrektrackermobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.github.slineham.startrektrackermobile.R
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun SettingsScreen(viewModel: TrackerViewModel = viewModel(), navController: NavController) {

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

            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111111), shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFF222222), RoundedCornerShape(16.dp))
                .padding(15.dp)
            ) {
                Text(
                    text = "INFORMATION //",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = LcarsColours.Orange,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "This product uses the TMDB API but is not endorsed or certified by TMDB.",
                    fontFamily = AntonioFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = LcarsColours.TextSecondary,
                    letterSpacing = 1.sp,
                    lineHeight = 20.sp
                )
                Image(
                    modifier = Modifier.padding(0.dp, 15.dp, 0.dp, 10.dp),
                    painter = painterResource(id = R.drawable.tmdb_logo),
                    contentDescription = "TMDB Logo"
                )
            }
        }
    }
}