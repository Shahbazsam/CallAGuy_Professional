package com.example.callaguy_professional.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.callaguy_professional.core.presentation.AppDefaults


@Composable
fun SmartImageLoader(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    shape : Shape = RoundedCornerShape(12.dp),
    contentDescription : String? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }

        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid Image Size"))
                    }
            },
            onError = {
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        val painterState by painter.state.collectAsStateWithLifecycle()
        val transition by animateFloatAsState(
            targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
            animationSpec = tween(durationMillis = 800)
        )
        when (val result = imageLoadResult) {
            null -> {
                CircularProgressIndicator(Modifier.size(50.dp))
            }
            else -> {
                val imagePainter =
                    if (result.isSuccess) painter else painterResource(AppDefaults.imageErrorRes)
                val scale = 0.8f + (0.2f * transition)
                val contentScale =
                    if (result.isSuccess) ContentScale.Crop else ContentScale.Fit
                Image(
                    painter = imagePainter,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier
                        .clip(shape = shape)
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationX = (1f - transition) * 30f
                            scaleX = scale
                            scaleY = scale
                        }

                )
            }
        }
    }
}