package io.github.slineham.startrektrackermobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.slineham.startrektrackermobile.ui.theme.AntonioFontFamily
import io.github.slineham.startrektrackermobile.ui.theme.LcarsColours

@Composable
fun SeasonSelector(
    numberOfSeasons: Int,
    currentSeason: Int,
    onSeasonSelected: (Int) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .padding(15.dp)
    ) {
        Button(
            onClick = { menuExpanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = LcarsColours.Violet),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "SEASON $currentSeason",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = AntonioFontFamily,
            )
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false}
        ) {
            (1..numberOfSeasons).forEach { number ->
                DropdownMenuItem(
                    text = { Text(number.toString()) },
                    onClick = {
                        onSeasonSelected(number)
                        menuExpanded = false
                    }
                )
            }
        }
    }
}