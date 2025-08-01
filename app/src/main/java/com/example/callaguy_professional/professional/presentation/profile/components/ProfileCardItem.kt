package com.example.callaguy_professional.professional.presentation.profile.components

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.callaguy_professional.R
import com.example.callaguy_professional.core.presentation.components.SmartImageLoader
import com.example.callaguy_professional.professional.domain.ProfessionalProfileInfo
import com.example.callaguy_professional.ui.theme.TextSecondary

@Composable
fun ProfileCardItem(
    modifier: Modifier = Modifier,
    launcher: ActivityResultLauncher<PickVisualMediaRequest>,
    imageUri : Uri? = null,
    isLoading : Boolean,
    info : ProfessionalProfileInfo
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(32.dp)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        ) {
            if (isLoading){
                CircularProgressIndicator(
                    color = TextSecondary,
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                )
            }
            when{
                imageUri != null -> {
                    SmartImageLoader(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(2.dp , TextSecondary, CircleShape),
                        imageUrl = imageUri,
                        shape = CircleShape
                    )
                }
                !info.profilePicture.isNullOrBlank() -> {
                    SmartImageLoader(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(2.dp , TextSecondary, CircleShape),
                        imageUrl = info.profilePicture,
                        shape = CircleShape
                    )
                }
                else -> {
                    Image(
                        painter = painterResource(R.drawable.logo), // Replace with real image if available
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(2.dp, TextSecondary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            TextButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(50.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = TextSecondary
                )
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

}

