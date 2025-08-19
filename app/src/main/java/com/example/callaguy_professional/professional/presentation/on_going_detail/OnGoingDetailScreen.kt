package com.example.callaguy_professional.professional.presentation.on_going_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.presentation.components.ConfirmationScreen
import com.example.callaguy_professional.core.presentation.components.LoadingScreen
import com.example.callaguy_professional.professional.data.dto.ServiceRequestStatus
import com.example.callaguy_professional.professional.presentation.ServiceDetail.components.ServiceDetailBasicInfo
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingDivider
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingItem
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary


@Composable
fun OnGoingDetailRoot(
    viewModel: OnGoingDetailViewModel,
    onGoToOrders: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    when {
        state.isSuccessful -> {
            ConfirmationScreen(
                text = stringResource(R.string.status_updated_successfully),
                description = stringResource(R.string.the_job_has_been_completed_successfully) +
                        context.getString(R.string.email_description),
                label = context.getString(R.string.go_to_orders),
                onGoToOrders = onGoToOrders
            )
        }

        state.isLoading -> {
            LoadingScreen()
        }

        else -> {
            OnGoingDetailScreen(
                state = state,
                onUpdateStatusClick = {
                    viewModel.onAction(
                        OnGoingDetailAction.OnUpdateStatusClick(
                            it
                        )
                    )
                }
            )
        }
    }

}

@Composable
fun OnGoingDetailScreen(
    state: OnGoingState,
    onUpdateStatusClick: (serviceId: Int) -> Unit
) {
    state.service?.let { service ->
        Box(
            modifier = Modifier
                .background(Background)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
            ) {
                ServiceDetailBasicInfo(
                    modifier = Modifier
                        .weight(0.8f),
                    imageUrl = service.image,
                    subService = service.subService,
                    instruction = service.specialInstructions
                )
                Column(modifier = Modifier.weight(1.2f)) {
                    OnGoingDivider()
                    OnGoingItem(
                        label = stringResource(R.string.scheduled),
                        data = service.preferredDate.toString()
                    )
                    OnGoingItem(
                        label = stringResource(R.string.time),
                        data = service.preferredTime.toString()
                    )
                    OnGoingDivider()
                    OnGoingItem(label = stringResource(R.string.address), data = service.address)
                    OnGoingItem(
                        label = stringResource(R.string.amount),
                        data = service.amount.toString()
                    )
                }
            }
            if (service.status != ServiceRequestStatus.COMPLETED) {
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onClick = {
                        onUpdateStatusClick(service.id)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    ) {
                    Text(
                        text = stringResource(R.string.complete_job),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }
            }

        }
    }

}