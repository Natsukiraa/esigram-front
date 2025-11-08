package com.example.esigram.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val chatBubble: Color,
    val onlineBadge: Color
)

val LocalExtraColors = staticCompositionLocalOf {
    ExtraColors(
        chatBubble = Color.Unspecified,
        onlineBadge = Color.Unspecified
    )
}

object EsigramExtraColors {
    val chatBubble: Color
        @Composable get() = LocalExtraColors.current.chatBubble
    val onlineBadge: Color
        @Composable get() = LocalExtraColors.current.onlineBadge
}