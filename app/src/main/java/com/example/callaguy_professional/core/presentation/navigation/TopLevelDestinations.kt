package com.example.callaguy_professional.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector


enum class TopLevelDestinations(
    val label : String,
    val selectedIcon : ImageVector ,
    val unSelectedIcon : ImageVector,
    val route : Destinations
){
    Services(
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        route = Destinations.ServiceListRoute
    ),
    Orders(
        label = "My Orders",
        selectedIcon = Icons.AutoMirrored.Filled.EventNote,
        unSelectedIcon = Icons.AutoMirrored.Outlined.EventNote,
        route = Destinations.OrderListScreenRoute
    ),
    Profile(
        label = "Profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unSelectedIcon = Icons.Outlined.AccountCircle,
        route = Destinations.ProfileScreenRoute
    )
}