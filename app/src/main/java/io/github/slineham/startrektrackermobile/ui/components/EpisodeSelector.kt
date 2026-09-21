package io.github.slineham.startrektrackermobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeSelector(
    episodeNameList: List<Pair<Int, String>>,
    chosenEpisode: Int,
    onEpisodeSelected: (Int) -> Unit,
) {

    var showSheet by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier
            .padding(15.dp)
    ) {
        Button(
            onClick = {showSheet = true},
            colors = ButtonDefaults.buttonColors(containerColor = LcarsColours.Violet),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "EPISODE $chosenEpisode",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = AntonioFontFamily,
            )
        }

        if(showSheet) {
            ModalBottomSheet(
                onDismissRequest = {showSheet = false}
            ) {
                LazyColumn() {
                    items(episodeNameList) { (episodeNum, episodeName) ->
                        ListItem(
                            headlineContent = { Text("Episode $episodeNum - $episodeName")},
                            modifier = Modifier.clickable {
                                onEpisodeSelected(episodeNum)
                                showSheet = false
                            }
                        )
                    }
                }
            }
        }
    }
}