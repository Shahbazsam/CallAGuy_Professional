package com.example.callaguy_professional.core.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally


fun slideInRight(delay: Int = 200) = slideInHorizontally(
    animationSpec = tween(durationMillis = 400, delayMillis = delay),
    initialOffsetX = { it }
)

fun slideOutLeft(delay: Int = 200) = slideOutHorizontally(
    animationSpec = tween(durationMillis = 400, delayMillis = delay),
    targetOffsetX = { -it }
)
