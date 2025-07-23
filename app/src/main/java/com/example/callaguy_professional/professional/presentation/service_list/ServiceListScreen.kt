package com.example.callaguy_professional.professional.presentation.service_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.service_list.components.ServiceCard
import com.example.callaguy_professional.ui.theme.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceListScreenRoot(
    viewModel: ServiceRequestViewModel = koinViewModel(),
    onServiceClick: (ServiceRequests) -> Unit
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

    ServiceListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ServiceListAction.OnServiceRequestClick -> onServiceClick(action.serviceRequest)
            }
        }
    )
}


@Composable
fun ServiceListScreen(
    state: ServiceListState,
    onAction: (ServiceListAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Background)
            .fillMaxSize()
    ) {
        items(
            items = state.serviceList,
            key = { service ->
                service.id
            }
        ) { service ->
            ServiceCard(
                modifier = Modifier.padding(vertical = 12.dp),
                service = service,
                onClick = {
                    onAction(ServiceListAction.OnServiceRequestClick(service))
                }
            )
        }
    }
}