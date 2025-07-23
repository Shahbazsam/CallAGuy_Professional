package com.example.callaguy_professional.core.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.callaguy_professional.ui.theme.Background
import com.example.callaguy_professional.ui.theme.Primary

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val navBackStack by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStack?.destination

    val showBottomNav = TopLevelDestinations.entries
        .map { it.route::class }
        .any { route ->
            currentDestination?.hierarchy?.any {
                it.hasRoute(route)
            } == true
        }

    AnimatedVisibility(visible = showBottomNav) {
        BottomAppBar(
            containerColor = Background
        ) {
            TopLevelDestinations.entries.map { bottomNavigationItem ->

                val isSelected = currentDestination?.hierarchy?.any {
                    it.hasRoute(bottomNavigationItem.route::class)
                } == true

                if (currentDestination != null) {
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.DarkGray,
                            unselectedIconColor = Primary,
                            selectedTextColor = Color.DarkGray,
                            unselectedTextColor = Primary,
                            indicatorColor = Color.Transparent,
                            disabledIconColor = Color.LightGray.copy(alpha = 0.5f),
                            disabledTextColor = Color.LightGray.copy(alpha = 0.5f)
                        ),
                        selected = isSelected,
                        onClick = {
                            navController.navigate(bottomNavigationItem.route) {
                                popUpTo(Destinations.ServiceListRoute){
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) bottomNavigationItem.selectedIcon else bottomNavigationItem.unSelectedIcon,
                                contentDescription = bottomNavigationItem.label
                            )
                        },
                        alwaysShowLabel = isSelected,
                        label = {
                            Text(
                                bottomNavigationItem.label
                            )
                        }
                    )
                }
            }

        }
    }

}