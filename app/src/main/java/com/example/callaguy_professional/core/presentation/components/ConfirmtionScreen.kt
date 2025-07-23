package com.example.callaguy_professional.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary
import com.example.callaguy_professional.ui.theme.TextSecondary

@Composable
fun ConfirmationScreen(
    text : String,
    description: String,
    label : String,
    onGoToOrders : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.tick_circle),
                contentDescription = "Success Tick",
                modifier = Modifier
                    .size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                fontSize = 16.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onGoToOrders,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}