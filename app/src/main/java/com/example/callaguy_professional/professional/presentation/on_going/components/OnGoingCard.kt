package com.example.callaguy_professional.professional.presentation.on_going.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.callaguy_professional.core.presentation.components.SmartImageLoader
import com.example.callaguy_professional.professional.domain.ServiceRequests

@Composable
fun OnGoingCard(
    modifier: Modifier,
    service: ServiceRequests,
    onClick: (ServiceRequests) -> Unit,
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(service)
            }
            .fillMaxWidth()
            .height(102.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnGoingInfo(
                modifier = Modifier.weight(0.6369f),
                subService = service.subService,
                status = service.status,
                address = service.address
            )
            Spacer(Modifier.width(12.dp))
            SmartImageLoader(
                modifier = Modifier
                    .weight(0.3631f)
                    .size(width = 130.dp, height = 70.dp),
                imageUrl = service.image
            )
        }
    }
}
