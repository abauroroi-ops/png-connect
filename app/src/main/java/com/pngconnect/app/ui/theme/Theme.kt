package com.pngconnect.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PngGoldPrimary,
    onPrimary = PngBlackPrimary,
    primaryContainer = PngRedDark,
    onPrimaryContainer = TextWhite,
    secondary = PngRedPrimary,
    onSecondary = TextWhite,
    background = PngBlackPrimary,
    surface = PngBlackSurface,
    surfaceVariant = PngBlackCard,
    onBackground = TextWhite,
    onSurface = TextWhite,
    outline = BorderOutline
)

@Composable
fun PNGConnectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
