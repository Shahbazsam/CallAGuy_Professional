package com.example.callaguy_professional.core.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTitle(navController: NavController) {
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentDestinations : NavDestination? = navBackStack?.destination

    val headerTitle = TopLevelHeader.entries.firstOrNull {
        it.destination == currentDestinations
    }?.label

    headerTitle?.let {
        TopAppBar(
            title = {
                Text(
                    text = headerTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Background,
                titleContentColor = TextPrimary
            )
        )
    }

}