package com.example.callaguy_professional.professional.presentation.on_going_detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.ui.theme.TextPrimary
import com.example.callaguy_professional.ui.theme.TextSecondary

@Composable
fun OnGoingItem(
    label : String,
    data : String,
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .fillMaxWidth()
    ){
        Text(
            modifier = Modifier
                .weight(1f),
            text = label,
            fontSize = 16.sp,
            lineHeight = 21.sp,
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            modifier = Modifier
                .padding(start = 18.dp)
                .weight(2f),
            text = data,
            lineHeight = 21.sp ,
            fontSize = 16.sp,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}