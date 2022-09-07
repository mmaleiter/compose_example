package com.pixabay.ui.base

import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ScreenOrientationHandler(
    landscapeContent: @Composable () -> Unit,
    portraitContent: @Composable () -> Unit
) {
    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current
    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            landscapeContent()
        }
        else -> {
            portraitContent()
        }
    }
}
