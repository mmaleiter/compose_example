package com.pixabay.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.ColorInt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pixabay.ui.theme.provideTextStyle
import cut.the.crap.mylibrary.models.PixaBayItem
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    pixaBayItem: PixaBayItem,
    commonColor: (Color) -> Unit,
    onNavigate: (String) -> Unit) {

    val item : PixaBayItem = pixaBayItem
    var offsetY by remember { mutableStateOf(0f) }

    val systemUiController = rememberSystemUiController()

    val uriHandler = LocalUriHandler.current

    var textColor by remember {
        mutableStateOf(Color.Red)
    }

    var backgroundColor by remember {
        mutableStateOf(Color.White)
    }

    Column(modifier = Modifier
        .background(backgroundColor)
        .fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pixaBayItem.largeImageURL)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(android.R.string.cancel),
            contentScale = ContentScale.Crop,
            onSuccess = {
                val color = getMeanColor((it.result.drawable as BitmapDrawable).bitmap)
                            backgroundColor = Color(color)
                commonColor(Color(color))
                systemUiController.setSystemBarsColor(color = Color(color))
                textColor = Color(getContrastColor(color))
            },
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = offsetY
                }
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        offsetY += delta
                    },
                    onDragStarted = {
                    },
                    onDragStopped = {
                        offsetY = 0f
                    }
                )
                .fillMaxWidth()
                .height(244.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = item.user,
            style = provideTextStyle(color = textColor),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Has ${item.likes} likes.",
            style = provideTextStyle(color = textColor),
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Has ${item.downloads} downloads.",
            style = provideTextStyle(
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.2.em,
            ),
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            onClick = { uriHandler.openUri(item.pageURL) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                modifier = Modifier.background(backgroundColor),
                text = "Visit web site",
                style = provideTextStyle(
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit.Unspecified,
                )
            )

        }
    }

}

@ColorInt
fun getMeanColor(originalBitmap: Bitmap): Int {
    val scaledBitmap = Bitmap.createScaledBitmap(
        originalBitmap.copy(Bitmap.Config.ARGB_8888, true),
        1, 1, false)
    return scaledBitmap.getPixel(0, 0)
}

@ColorInt
fun getContrastColor(@ColorInt color: Int): Int {
    val a =
        1 - (0.299 * android.graphics.Color.red(color) + 0.587 * android.graphics.Color.green(color) + 0.114 * android.graphics.Color.blue(color)) / 255
    val d: Int = if (a < 0.5) {
        0
    } else {
        255
    }
    return android.graphics.Color.rgb(d, d, d)
}