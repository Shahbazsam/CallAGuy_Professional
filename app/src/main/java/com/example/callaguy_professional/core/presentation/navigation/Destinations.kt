package com.example.callaguy_professional.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {

    @Serializable
    object SplashScreenRoute : Destinations

    @Serializable
    object NotYetApprovedYetRoute : Destinations

    @Serializable
    object ServiceListGraph : Destinations

    @Serializable
    object ServiceListRoute : Destinations

    @Serializable
    object DetailServiceListScreenRoute : Destinations

    @Serializable
    object OnGoingGraph : Destinations

    @Serializable
    object  OrderListScreenRoute : Destinations

    @Serializable
    object DetailOrderListScreenRoute : Destinations

    @Serializable
    object ProfileScreenRoute : Destinations
}


