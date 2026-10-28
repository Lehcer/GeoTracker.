
package com.rechel.geotracker.navigation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rechel.geotracker.ui.locations.LocationsViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rechel.geotracker.ui.login.LoginScreen
import com.rechel.geotracker.ui.dashboard.DashboardScreen
import com.rechel.geotracker.ui.location.LocationScreen
import com.rechel.geotracker.ui.locations.LocationsScreen
import com.rechel.geotracker.ui.map.MapScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                onCapture = {
                    navController.navigate("capture")
                },
                onList = {
                    navController.navigate("locations")
                },
                onMap = {
                    navController.navigate("map")
                }
            )
        }

        composable("capture") {
            LocationScreen()
        }

        composable("locations") {
            LocationsScreen()
        }

        composable("map") {
            val locationsViewModel: LocationsViewModel = viewModel()

            MapScreen(
                viewModel = locationsViewModel
            )
        }
    }
}
