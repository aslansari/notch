package com.aslansari.notch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = ColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD0B3FF),
    onPrimaryContainer= Color(0xFF21005D),
    inversePrimary= Color(0xFF6750A4),
    secondary= Color(0xFF625B71),
    onSecondary= Color(0xFFFFFFFF),
    secondaryContainer= Color(0xFFE8DEF8),
    onSecondaryContainer= Color(0xFF1D192B),
    tertiary= Color(0xFF7D5260),
    onTertiary= Color(0xFFFFFFFF),
    tertiaryContainer= Color(0xFFFFD8E4),
    onTertiaryContainer= Color(0xFF31111D),
    background= Color(0xFFFFFBFE),
    onBackground= Color(0xFF1C1B1F),
    surface= Color(0xFFFFFBFE),
    onSurface= Color(0xFF1C1B1F),
    surfaceVariant= Color(0xFFFFFBFE),
    onSurfaceVariant= Color(0xFF49454F),
    inverseSurface= Color(0xFF000000),
    inverseOnSurface= Color(0xFFFFFFFF),
    error= Color(0xFFB3261E),
    onError= Color(0xFFFFFFFF),
    errorContainer= Color(0xFFF9DEDC),
    onErrorContainer= Color(0xFF410E0B),
    outline = Color(0xFF79747E)
)

private val DarkColorPalette = ColorScheme(
    primary = Color(0xFFD0BCFF),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer= Color(0xFFEADDFF),
    inversePrimary= Color(0xFF6750A4),
    secondary= Color(0xFFCCC2DC),
    onSecondary= Color(0xFF332D41),
    secondaryContainer= Color(0xFF4A4458),
    onSecondaryContainer= Color(0xFFE8DEF8),
    tertiary= Color(0xFFEFB8C8),
    onTertiary= Color(0xFF492532),
    tertiaryContainer= Color(0xFF633B48),
    onTertiaryContainer= Color(0xFFFFD8E4),
    background= Color(0xFF1C1B1F),
    onBackground= Color(0xFFE6E1E5),
    surface= Color(0xFF1C1B1F),
    onSurface= Color(0xFFE6E1E5),
    surfaceVariant= Color(0xFF49454F),
    onSurfaceVariant= Color(0xFFCAC4D0),
    inverseSurface= Color(0xFFFFFFFF),
    inverseOnSurface= Color(0xFF000000),
    error= Color(0xFFF2B8B5),
    onError= Color(0xFF601410),
    errorContainer= Color(0xFF8C1D18),
    onErrorContainer= Color(0xFFF9DEDC),
    outline = Color(0xFF938F99)
)

@Composable
fun NotchTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}