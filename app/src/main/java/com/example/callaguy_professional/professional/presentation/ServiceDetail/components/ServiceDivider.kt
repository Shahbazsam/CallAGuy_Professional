package com.example.callaguy_professional.professional.presentation.ServiceDetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.callaguy_professional.ui.theme.Divider

@Composable
fun ServiceDivider() {
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
    ){
        HorizontalDivider(
            modifier = Modifier
                .weight(1f),
            thickness = 1.5.dp,
            color = Divider
        )
        Spacer(Modifier.width(24.dp))
        HorizontalDivider(
            modifier = Modifier
                .weight(2f),
            thickness = 1.5.dp,
            color = Divider
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview(modifier: Modifier = Modifier) {
    ServiceDivider()
}

