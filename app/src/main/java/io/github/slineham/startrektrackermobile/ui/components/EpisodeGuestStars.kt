package io.github.slineham.startrektrackermobile.ui.components

import android.graphics.Paint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.github.slineham.startrektrackermobile.api.GuestStar
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours

@Composable
fun EpisodeGuestStars(guestStars: List<GuestStar>) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)
    ) {
        Text(
            text = "GUEST STARS //",
            fontFamily = AntonioFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = LcarsColours.LightBlue,
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        if (guestStars.isEmpty()) {
            Text("There are no guest stars in this episode.")
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                guestStars.forEach { guestStar ->
                    GuestStarDetails(guestStar)
                }
            }
        }
    }
}

@Composable
fun GuestStarDetails(guestStar: GuestStar) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(width = 100.dp, height = 130.dp)
        ) {
            if (guestStar.profilePath.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile picture not available.",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp).align(Alignment.Center)
                )
            } else {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w185${guestStar.profilePath}",
                    contentDescription = "Actor profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = guestStar.name,
            textAlign = TextAlign.Center,
            fontFamily = AntonioFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = LcarsColours.TextPrimary,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = guestStar.character,
            textAlign = TextAlign.Center,
            fontFamily = AntonioFontFamily,
            fontSize = 11.sp,
            color = LcarsColours.Violet,
            overflow = TextOverflow.Ellipsis
        )
    }
}

