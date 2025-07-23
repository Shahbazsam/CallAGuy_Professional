package com.example.callaguy_professional.professional.presentation.splash

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToMain : () -> Unit,
    onNavigateToNotApproved : () -> Unit
) {
    val size = remember { Animatable(initialValue = 75.dp , Dp.VectorConverter)  }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var timerFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onAction(SplashAction.CheckApproval)
        launch {
            while (isActive){
                size.animateTo(
                    targetValue = 200.dp,
                    animationSpec = tween(durationMillis = 800 , easing = LinearEasing)
                )
                size.animateTo(
                    targetValue = 150.dp,
                    animationSpec = tween(durationMillis = 800 , easing = LinearEasing)
                )
            }
        }
        delay(2500L)
        timerFinished = true
    }
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(timerFinished , state.isApproved) {
        if (timerFinished && state.isApproved != null){
            if (state.isApproved == true) onNavigateToMain() else onNavigateToNotApproved()
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(size.value)
        )
    }

}