package com.example.callaguy_professional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.callaguy_professional.app.App
import com.example.callaguy_professional.core.presentation.navigation.BottomNavigationBar
import com.example.callaguy_professional.core.presentation.navigation.TopAppBarTitle
import com.example.callaguy_professional.professional.presentation.service_list.components.ServiceCard
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.CallAGuy_ProfessionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CallAGuy_ProfessionalTheme {
                Scaffold(
                    containerColor = Background,
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopAppBarTitle(
                            navController = navController
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    App(
                        navController = navController,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }

            }
        }
    }
}

