package com.example.esigram.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF40CFCE),
    onPrimary = Color(0xFF003737),
    primaryContainer = Color(0xFF005050),
    onPrimaryContainer = Color(0xFF5FE6E6),

    secondary = Color(0xFFB0CCCC),
    onSecondary = Color(0xFF1C3535),
    secondaryContainer = Color(0xFF334B4B),
    onSecondaryContainer = Color(0xFFCCE9E9),

    background = Color(0xFF111414),
    surface = Color(0xFF191C1C),
    onBackground = Color(0xFFE1E3E3),
    onSurface = Color(0xFFE1E3E3),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006A6A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF5FE6E6),
    onPrimaryContainer = Color(0xFF002020),

    secondary = Color(0xFF4A6363),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE9E9),
    onSecondaryContainer = Color(0xFF052020),

    background = Color(0xFFFCFCFC),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF191C1C),
    onSurface = Color(0xFF191C1C),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
)

@Composable
fun EsigramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}