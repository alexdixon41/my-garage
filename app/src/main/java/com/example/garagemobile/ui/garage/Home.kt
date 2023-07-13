package com.example.garagemobile.ui.garage

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.addHomeGraph(
    onVehicleSelected: (Long, NavBackStackEntry) -> Unit
) {
    composable("garage_overview") { from ->
        GarageOverview(onVehicleClick = { id -> onVehicleSelected(id, from) })
    }
}
