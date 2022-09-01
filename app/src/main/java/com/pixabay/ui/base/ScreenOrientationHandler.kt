package com.pixabay.ui.base

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ScreenOrientationHandler(
    landscapeContent: @Composable (modifier: Modifier) -> Unit,
    portraitContent: @Composable (modifier: Modifier) -> Unit
) {
    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current

// If our configuration changes then this will launch a new coroutine scope for it
    LaunchedEffect(configuration) {
        // Save any changes to the orientation value on the configuration object
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            landscapeContent(Modifier)
        }
        else -> {
            portraitContent(Modifier)
        }
    }
}

@Composable
fun ConfigChangeExample() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Text("Landscape")
        }
        else -> {
            Text("Portrait")
        }
    }
}