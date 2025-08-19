package com.example.callaguy_professional.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.callaguy_professional.core.presentation.navigation.Destinations
import com.example.callaguy_professional.core.presentation.slideInRight
import com.example.callaguy_professional.core.presentation.slideOutLeft
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailAction
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailRoot
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailViewModel
import com.example.callaguy_professional.professional.presentation.login.LoginScreenRoot
import com.example.callaguy_professional.professional.presentation.on_going.OnGoingRoot
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailAction
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailRoot
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailViewModel
import com.example.callaguy_professional.professional.presentation.profile.ProfileScreenRoot
import com.example.callaguy_professional.professional.presentation.service_list.ServiceListScreenRoot
import com.example.callaguy_professional.professional.presentation.sharedViewModel.OnGoingSharedViewModel
import com.example.callaguy_professional.professional.presentation.sharedViewModel.ServiceListSharedViewModel
import com.example.callaguy_professional.professional.presentation.splash.NotApprovedScreen
import com.example.callaguy_professional.professional.presentation.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
     NavHost(
        navController = navController,
        startDestination = Destinations.SplashScreenRoute,
        modifier = modifier
    ) {
        composable<Destinations.SplashScreenRoute> {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Destinations.ServiceListGraph){
                        popUpTo(Destinations.SplashScreenRoute){
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Destinations.LoginScreenRoute){
                        popUpTo(Destinations.SplashScreenRoute){
                            inclusive = true
                        }
                    }
                },
                onNavigateToNotApproved = {
                    navController.navigate(Destinations.NotYetApprovedYetRoute) {
                        popUpTo(Destinations.SplashScreenRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destinations.LoginScreenRoute>(
            enterTransition = { slideInRight() },
            exitTransition = { slideOutLeft() }
        ) {
            LoginScreenRoot(
                onNavigateToMain = {
                    navController.navigate(Destinations.ServiceListGraph){
                        popUpTo(Destinations.LoginScreenRoute){
                            inclusive = true
                        }
                    }
                },
                onNavigateToNotApproved = {
                    navController.navigate(Destinations.NotYetApprovedYetRoute){
                        popUpTo(Destinations.LoginScreenRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destinations.NotYetApprovedYetRoute>(
            enterTransition = { slideInRight() }
        ) {
            NotApprovedScreen()
        }

        navigation<Destinations.ServiceListGraph>(
            startDestination = Destinations.ServiceListRoute
        ) {
            composable<Destinations.ServiceListRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() },
                popEnterTransition = {slideInRight()}
            ) {
                val serviceListSharedViewModel =
                    it.sharedKoinViewModel<ServiceListSharedViewModel>(navController)

                LaunchedEffect(true) {
                    serviceListSharedViewModel.onSelectedService(null)
                }

                ServiceListScreenRoot(
                    onServiceClick = { service ->
                        serviceListSharedViewModel.onSelectedService(service)
                        navController.navigate(
                            Destinations.DetailServiceListScreenRoute
                        )
                    }
                )
            }
            composable<Destinations.DetailServiceListScreenRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() }
            ) {

                val serviceListSharedViewModel =
                    it.sharedKoinViewModel<ServiceListSharedViewModel>(navController)
                val viewmodel = koinViewModel<ServiceDetailViewModel>()
                val selectedService by serviceListSharedViewModel.selectedService.collectAsStateWithLifecycle()

                LaunchedEffect(selectedService) {
                    selectedService?.let {
                        viewmodel.onAction(ServiceDetailAction.OnServiceDetailChange(it))
                    }
                }

                ServiceDetailRoot(
                    viewModel = viewmodel,
                    onGoToOrders = {
                        navController.navigate(Destinations.OnGoingGraph){
                            popUpTo(Destinations.ServiceListGraph){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        navigation<Destinations.OnGoingGraph>(
            startDestination = Destinations.OrderListScreenRoute
        ) {
            composable<Destinations.OrderListScreenRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() },
                popEnterTransition = {slideInRight()}
            ) {
                val onGoingSharedViewModel =
                    it.sharedKoinViewModel<OnGoingSharedViewModel>(navController)

                LaunchedEffect(true) {
                    onGoingSharedViewModel.onSelectedOnGoing(null)
                }
                OnGoingRoot(
                    onOrderClick = { service ->
                        onGoingSharedViewModel.onSelectedOnGoing(service)
                        navController.navigate(Destinations.DetailOrderListScreenRoute)
                    }
                )
            }
            composable<Destinations.DetailOrderListScreenRoute>(
                enterTransition = { slideInRight() },
                exitTransition = { slideOutLeft() },

            ) {

                val onGoingSharedViewModel =
                    it.sharedKoinViewModel<OnGoingSharedViewModel>(navController)
                val viewmodel = koinViewModel<OnGoingDetailViewModel>()
                val selectedService by onGoingSharedViewModel.selectedOnGoing.collectAsStateWithLifecycle()

                LaunchedEffect(selectedService) {
                    selectedService?.let {
                        viewmodel.onAction(OnGoingDetailAction.OnGoingDetailChange(it))
                    }
                }

                OnGoingDetailRoot(
                    viewModel = viewmodel,
                    onGoToOrders = {
                        navController.navigate(Destinations.OnGoingGraph){
                            popUpTo(Destinations.ServiceListGraph){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable<Destinations.ProfileScreenRoute>(
            enterTransition = { slideInRight() },
            exitTransition = { slideOutLeft() },
        ){
            ProfileScreenRoot(
                onLogOutClick = {
                    navController.navigate(Destinations.LoginScreenRoute){
                        popUpTo(Destinations.ProfileScreenRoute){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
) : T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>( )
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}