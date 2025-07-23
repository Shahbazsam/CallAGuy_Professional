package com.example.callaguy_professional.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary

@Composable
fun LoadingScreen() {
    Column (
        modifier = Modifier
            .background(Background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(150.dp),
            color = Primary
        )
    }
}