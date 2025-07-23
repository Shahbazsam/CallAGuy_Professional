package com.example.callaguy_professional.core.presentation.navigation

enum class TopLevelHeader(
    val label : String ,
    val destination : Destinations
) {
    Tasks(
        label = "Tasks",
        destination = Destinations.ServiceListRoute
    ),
    TaskDetail(
        label = "Task Details",
        destination = Destinations.DetailServiceListScreenRoute
    ),
    OnGoing(
        label = "Ongoing",
        destination = Destinations.OrderListScreenRoute
    ),
    OnGoingDetail(
        label = "Ongoing Details",
        destination = Destinations.DetailOrderListScreenRoute
    ),
    Profile(
        label = "Profile",
        destination = Destinations.ProfileScreenRoute
    )
}