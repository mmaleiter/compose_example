package com.pixabay.ui.base

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.GenericFontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.disk.DiskCache
import coil.memory.MemoryCache


@OptIn(ExperimentalCoilApi::class)
fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.2)
                .build()
        }
        .build()
}

@Composable
fun provideTextStyle(
    color: Color = MaterialTheme.colors.onBackground,
    fontSize: TextUnit = 16.sp,
    fontFamily: GenericFontFamily = FontFamily.Monospace,
    fontWeight: FontWeight = FontWeight.W800,
    fontStyle: FontStyle = FontStyle.Italic,
    letterSpacing: TextUnit = 0.5.em,
) : TextStyle {
    return TextStyle(
        color = color,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing,
    )
}