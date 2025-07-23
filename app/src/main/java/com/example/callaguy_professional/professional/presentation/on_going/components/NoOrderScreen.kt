package com.example.callaguy_professional.professional.presentation.on_going.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.ToggleButtonContent

@Composable
fun NoOrderScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp)
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.no_orders),
            contentDescription = stringResource(R.string.no_orders),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .size(400.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(R.string.no_orders_yet),
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4A739C)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.you_don_t_have_any_upcoming_or_past_orders),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = ToggleButtonContent,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}