package com.example.callaguy_professional.professional.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.ui.theme.TextPrimary
import com.example.callaguy_professional.ui.theme.TextSecondary

@Composable
fun ServiceChip(
    label : String,
    services : List<String>
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
        Spacer(Modifier.padding(2.dp))
        FlowRow (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(2f)
        ){
            services.forEach { service ->
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "$service, ",
                    lineHeight = 21.sp ,
                    fontSize = 16.sp,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

        }


    }


}