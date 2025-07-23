package com.example.callaguy_professional.professional.presentation.service_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.presentation.components.SmartImageLoader
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.ui.theme.TextPrimary
import com.example.callaguy_professional.ui.theme.TextSecondary


@Composable
fun ServiceCard(
    modifier: Modifier = Modifier,
    onClick : (ServiceRequests) -> Unit,
    service : ServiceRequests
) {
    Card(
        modifier = modifier
            .clickable{
                onClick(service)
            }
            .fillMaxWidth()
            .height(72.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmartImageLoader(
                modifier = Modifier
                    .size(56.dp),
                imageUrl = service.image
            )
            Spacer(Modifier.width(12.dp))
            Column (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = service.subService ,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = service.specialInstructions ?: "No Instruction Given",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = service.amount.toString(),
                color = TextPrimary,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}