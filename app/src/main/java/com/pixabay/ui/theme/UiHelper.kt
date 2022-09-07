package com.pixabay.ui.theme

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