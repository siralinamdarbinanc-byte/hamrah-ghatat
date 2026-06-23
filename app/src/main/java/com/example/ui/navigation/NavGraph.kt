package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.BrandSelectionScreen
import com.example.ui.screens.CategoryScreen
import com.example.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object BrandSelection : Screen("brand_selection")
    object IranKhodro : Screen("iran_khodro")
    object Saipa : Screen("saipa")
    object OtherCars : Screen("other_cars")
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToSelection = {
                    navController.navigate(Screen.BrandSelection.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.BrandSelection.route) {
            BrandSelectionScreen(
                onNavigateToIranKhodro = {
                    navController.navigate(Screen.IranKhodro.route)
                },
                onNavigateToSaipa = {
                    navController.navigate(Screen.Saipa.route)
                },
                onNavigateToOtherCars = {
                    navController.navigate(Screen.OtherCars.route)
                }
            )
        }
        composable(Screen.IranKhodro.route) {
            CategoryScreen(
                brandId = "iran_khodro",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Saipa.route) {
            CategoryScreen(
                brandId = "saipa",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.OtherCars.route) {
            CategoryScreen(
                brandId = "other_cars",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
