package com.example.callaguy_professional.professional.presentation.on_going_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.core.presentation.components.SmartImageLoader
import com.example.callaguy_professional.ui.theme.TextPrimary


@Composable
fun OnGoingDetailBasicInfo(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    subService: String,
    instruction: String?
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmartImageLoader(
            modifier = Modifier
                .padding(12.dp)
                .size(120.dp)
                .clip(shape = RoundedCornerShape(12.dp)),
            imageUrl = imageUrl,
            contentDescription = subService
        )
        Text(
            modifier = Modifier
                .padding(12.dp),
            text = subService,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        instruction?.let {
            Text(
                modifier = Modifier
                    .padding(bottom = 12.dp),
                lineHeight = 24.sp,
                text = it,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }


}