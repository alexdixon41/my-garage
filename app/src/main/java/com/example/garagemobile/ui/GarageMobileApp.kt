package com.example.garagemobile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.garagemobile.ui.vehicledetail.VehicleDetail
import com.example.garagemobile.ui.garage.addHomeGraph
import com.example.garagemobile.ui.theme.GarageMobileTheme

@Composable
fun GarageMobileApp() {
    GarageMobileTheme {
        val appState = rememberGarageMobileAppState()
        NavHost(
            navController = appState.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            garageMobileNavGraph(
                onVehicleSelected = appState::navigateToVehicleDetail,
                upPress = appState::upPress
            )
        }
    }
}

private fun NavGraphBuilder.garageMobileNavGraph(
    onVehicleSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = "garage_overview"
    ) {
        addHomeGraph(onVehicleSelected)
    }
    composable(
        "${MainDestinations.VEHICLE_DETAIL_ROUTE}/{${MainDestinations.VEHICLE_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.VEHICLE_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val vehicleId = arguments.getLong(MainDestinations.VEHICLE_ID_KEY)
        VehicleDetail(vehicleId, upPress)
    }
}

