package com.example.callaguy_professional.professional.presentation.on_going.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.ui.theme.TextPrimary
import com.example.callaguy_professional.ui.theme.TextSecondary


@Composable
fun OnGoingInfo(
    modifier: Modifier,
    subService : String,
    status: ServiceRequestStatus,
    address : String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = status.toString()  ,
            color = TextSecondary,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = subService ,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = address,
            color = TextSecondary,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


