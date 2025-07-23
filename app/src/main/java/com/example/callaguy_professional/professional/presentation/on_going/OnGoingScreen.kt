package com.example.callaguy_professional.professional.presentation.on_going

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.presentation.components.ToggleButtons
import com.example.callaguy_professional.professional.domain.ServiceRequests
import com.example.callaguy_professional.professional.presentation.on_going.components.NoOrderScreen
import com.example.callaguy_professional.professional.presentation.on_going.components.OnGoingCard
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.TextSecondary
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnGoingRoot(
    viewModel: OnGoingViewModel = koinViewModel(),
    onOrderClick: (ServiceRequests) -> Unit
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
    OnGoingScreen(
        state = state,
        onCardClick = { action ->
            when (action) {
                is OnGoingOrderAction.OnOrderClick -> onOrderClick(action.order)
            }
        }
    )
}


@Composable
fun OnGoingScreen(
    state: OnGoingScreenState,
    onCardClick: (OnGoingOrderAction) -> Unit
) {
    var acceptedOrPast by remember { mutableStateOf(false) }
    val current by remember(state, acceptedOrPast) {
        mutableStateOf(if (!acceptedOrPast) state.accepted else state.past)
    }

    Column(
        modifier = Modifier
            .background(Background)
            .fillMaxSize()
    ) {
        ToggleButtons(
            leftText = stringResource(R.string.accepted),
            rightText = stringResource(R.string.completed),
            acceptedOrCompleted = acceptedOrPast
        ) {
            acceptedOrPast = it
        }
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    Modifier.size(200.dp),
                    color = TextSecondary
                )
            }
            else -> {
                if (current.isNotEmpty()) {
                    OnGoingList(
                        services = current,
                        onCardClick = onCardClick
                    )
                } else {
                    NoOrderScreen()
                }
            }
        }
    }
}

@Composable
fun OnGoingList(
    services: List<ServiceRequests>,
    onCardClick: (OnGoingOrderAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = services,
            key = { service ->
                service.id
            }
        ) { service ->
            OnGoingCard(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                service = service,
                onClick = {
                    onCardClick(OnGoingOrderAction.OnOrderClick(service))
                }
            )
        }
    }
}