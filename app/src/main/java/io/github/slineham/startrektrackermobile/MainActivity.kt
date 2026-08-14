package io.github.slineham.startrektrackermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.slineham.startrektrackermobile.ui.Navigator
import io.github.slineham.startrektrackermobile.ui.theme.StarTrekTrackerMobileTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarTrekTrackerMobileTheme {
                Navigator()
            }
        }
    }
}