package com.example.callaguy_professional.professional.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.R
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingDivider
import com.example.callaguy_professional.professional.presentation.on_going_detail.components.OnGoingItem
import com.example.callaguy_professional.professional.presentation.profile.components.ProfileCardItem
import com.example.callaguy_professional.professional.presentation.profile.components.ServiceChip
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary
import com.example.callaguy_professional.ui.theme.TextSecondary
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = koinViewModel(),
    onLogOutClick : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        action = { action ->
            viewModel.onAction(action)
        },
        onLogOutClick = onLogOutClick
    )
}


@Composable
fun ProfileScreen(
    state : ProfileState,
    action: (ProfileAction) -> Unit,
    onLogOutClick: () -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                action(ProfileAction.OnProfileImageChanged(it , context))
                imageUri = uri
            }
        }
    )
    state.info?.let { info ->
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
                    launcher = singleImagePickerLauncher,
                    imageUri = imageUri,
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
}

