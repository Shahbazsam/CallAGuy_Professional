package com.example.callaguy_professional.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.Black,
    secondary = NeutralButton,
    background = Color(0xFF121417),
    onBackground = Color.White,
    surface = Color(0xFF1A1C1E),
    onSurface = Color.White,
    outline = Color(0xFF2E2E2E)
)


val LightColorScheme = lightColorScheme(
    primary = Primary,                  // #0D80F2 — Accept Button
    onPrimary = Color.White,            // Text color on primary buttons
    secondary = NeutralButton,          // #F0F2F5 — Decline Button BG
    onSecondary = TextPrimary,          // #121417 — Text on Decline button
    background = Background,            // #FFFFFF — Screen background
    onBackground = TextPrimary,         // Text color on white background
    surface = Background,               // Usually same as background
    onSurface = TextSecondary,          // Secondary text (e.g. labels)
    outline = Divider                   // #E5E8EB — Divider lines
)


@Composable
fun CallAGuy_ProfessionalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}