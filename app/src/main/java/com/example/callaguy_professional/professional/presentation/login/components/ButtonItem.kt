package com.example.callaguy_professional.professional.presentation.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.professional.presentation.login.LoginFormEvent

@Composable
fun ButtonItem(
    isLoading : Boolean,
    onClick : (LoginFormEvent) -> Unit
    ) {
    Column (
        verticalArrangement = Arrangement.Bottom

    ){
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .size(width = 55.dp, height = 55.dp),
            onClick = {
                onClick(LoginFormEvent.Submit)
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    color = Color(0xFFFFFFFF)
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFFFFFFFF),
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                            .size(20.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(50.dp))
        Row(
            modifier =Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 60.dp)
        ){
            Text(
                modifier = Modifier
                    .padding(top = 4.dp ),
                text = stringResource(R.string.don_t_have_a_account),
                style = MaterialTheme.typography.displayMedium,
                fontSize = 18.sp,
                color = Color(0xFF777777),
                fontWeight = FontWeight.Normal
            )
            Text(
                modifier = Modifier
                    .clickable { }
                    .padding(top = 4.dp),
                text = stringResource(R.string.register),
                style = MaterialTheme.typography.displayMedium,
                fontSize = 18.sp,
                color = Color(0xFF4A90E2),
                fontWeight = FontWeight.Normal
            )
        }

    }
}