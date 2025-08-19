package com.example.callaguy_professional.professional.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy_professional.R
import com.example.callaguy_professional.professional.presentation.login.components.ButtonItem
import com.example.callaguy_professional.professional.presentation.login.components.LoginTextField
import com.example.callaguy_professional.professional.presentation.login.components.LogoItem
import com.example.callaguy_professional.ui.theme.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToNotApproved : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.isLoginError) {
        if (state.isLoginError != null) {
            Toast.makeText(
                context,
                state.isLoginError,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    LaunchedEffect(state.isLoginSuccessful ) {
        if (state.isLoginSuccessful ) {
            if (state.isApproved){
                Toast.makeText(
                    context,
                    context.getString(R.string.login_successful),
                    Toast.LENGTH_LONG
                ).show()
                onNavigateToMain()
            }else {
                Toast.makeText(
                    context,
                    context.getString(R.string.login_successful),
                    Toast.LENGTH_LONG
                ).show()
                onNavigateToNotApproved()
            }
        }
    }
    LoginScreen(
        state = state,
        onEvent = { event ->
            viewModel.onEvent(event)
        }
    )

}


@Composable
fun LoginScreen(
    state: LoginFormState,
    onEvent: (LoginFormEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LogoItem()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 28.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.login_in_to_continue),
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 18.sp,
            color = Color(0xFF777777),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        LoginTextField(
            value = state.email,
            onValueChange = {
                onEvent(LoginFormEvent.EmailChanged(it))
            },
            label = "E-mail",
            isPassword = false,
            stateError = state.emailError
        )
        LoginTextField(
            stateError = state.passwordError,
            value = state.password,
            onValueChange = {
                onEvent(LoginFormEvent.PasswordChanged(it))
            },
            label = "Password",
            isPassword = true,
        )
        Spacer(Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.displayMedium,
            fontSize = 18.sp,
            color = Color(0xFF4A90E2),
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.weight(1f))
        ButtonItem(
            isLoading = state.isLoading,
            onClick = {
                onEvent(LoginFormEvent.Submit)
            }
        )
    }
}

@Preview
@Composable
fun Preview3(modifier: Modifier = Modifier) {
    LoginScreen(
        state = LoginFormState(),
        onEvent = {
            LoginFormEvent.EmailChanged(email = "")
        }
    )
}