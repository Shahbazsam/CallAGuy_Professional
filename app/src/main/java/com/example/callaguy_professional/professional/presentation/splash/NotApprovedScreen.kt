package com.example.callaguy_professional.professional.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.ui.theme.Background

@Composable
fun NotApprovedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.not_approved),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.you_re_not_approved_yet),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding( horizontal = 18.dp , vertical = 8.dp),
            text = stringResource(R.string.once_approved_you_will_receive),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .padding( horizontal = 18.dp ),
            text = stringResource(R.string.an_confirmation_email),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp
        )

    }
}

@Preview
@Composable
fun Preview2(modifier: Modifier = Modifier) {
    NotApprovedScreen()
}