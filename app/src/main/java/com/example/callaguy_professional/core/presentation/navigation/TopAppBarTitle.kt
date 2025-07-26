package com.example.callaguy_professional.core.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTitle(navController: NavController) {
    val navBackStack by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStack?.destination


    val currentHeader = TopLevelHeader.entries.firstOrNull { header ->
        currentDestination?.hierarchy?.any {
            it.hasRoute(header.destination::class)
        } == true
    }
    currentHeader?.let { header ->
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = header.label,
                    fontSize = 22.sp,
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