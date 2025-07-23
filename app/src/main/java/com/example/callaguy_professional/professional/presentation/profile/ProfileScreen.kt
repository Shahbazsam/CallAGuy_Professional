package com.example.callaguy_professional.professional.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingDivider
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingItem
import com.example.callaguy_professional.professional.presentation.profile.components.ProfileCardItem
import com.example.callaguy_professional.professional.presentation.profile.components.ServiceChip
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary
import com.example.callaguy_professional.ui.theme.TextSecondary


@Composable
fun ProfileScreen(
    info: ProfessionalProfileInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileCardItem(
                isLoading = true,
                info = info
            )
            Text(
                text = stringResource(R.string.approved),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(
            modifier = Modifier
                .weight(1.7f)
                .verticalScroll(rememberScrollState())
        ) {
            OnGoingDivider()
            OnGoingItem(
                label = stringResource(R.string.professional_name),
                data = info.userName
            )
            OnGoingDivider()
            OnGoingItem(
                label = stringResource(R.string.e_mail),
                data = info.email
            )
            OnGoingDivider()
            OnGoingItem(
                label = stringResource(R.string.experience_year_s),
                data = info.experience.toString()
            )
            OnGoingDivider()
            ServiceChip(
                label = stringResource(R.string.services_offered),
                services = info.services
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.log_out),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }

}


@Preview
@Composable
fun Preview2(modifier: Modifier = Modifier) {
    ProfileScreen(
        ProfessionalProfileInfo(
            userName = "shahbaz",
            email = "someemem",
            experience = 1,
            isApproved = true,
            profilePicture = null ,
            services = listOf(
                "Electrical", "Painting" , "Const",
                "Electrical", "Painting" , "Construction",
                "Electrical", "Painting" , "Construction",
            )
        )
    )
}