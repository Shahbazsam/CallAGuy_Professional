package com.example.callaguy_professional.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.ToggleButtonColor
import com.example.callaguy_professional.ui.theme.ToggleButtonContent

@Composable
fun ToggleButtons(
    leftText : String,
    rightText : String,
    acceptedOrCompleted : Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        Modifier
            .padding(start = 16.dp , end = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Background)
    ) {
        val buttonModifier = Modifier
            .weight(1f)
            .padding(2.dp)

        Button(
            onClick = { onToggle(false) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!acceptedOrCompleted) ToggleButtonColor else Color.Transparent,
                contentColor = ToggleButtonContent
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = null
        ) {
            Text(leftText)
        }

        Button(
            onClick = { onToggle(true) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (acceptedOrCompleted) ToggleButtonColor else Color.Transparent,
                contentColor = ToggleButtonContent
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = null
        ) {
            Text(rightText)
        }
    }
}