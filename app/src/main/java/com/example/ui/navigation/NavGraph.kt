package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    object Search : Screen("search")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("خانه", Icons.Default.Home, Screen.BrandSelection.route),
    BottomNavItem("جستجو", Icons.Default.Search, Screen.Search.route),
    BottomNavItem("سفارش‌ها", Icons.Default.ShoppingCart, Screen.Orders.route),
    BottomNavItem("پروفایل", Icons.Default.Person, Screen.Profile.route)
)

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.BrandSelection.route,
        Screen.Search.route,
        Screen.Orders.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFF0D1B2A),
                    contentColor = Color.White
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Screen.BrandSelection.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF3B82F6),
                                selectedTextColor = Color(0xFF3B82F6),
                                unselectedIconColor = Color(0xFFA7B1C2),
                                unselectedTextColor = Color(0xFFA7B1C2),
                                indicatorColor = Color(0xFF142033)
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(paddingValues)
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
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Saipa.route) {
                CategoryScreen(
                    brandId = "saipa",
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.OtherCars.route) {
                CategoryScreen(
                    brandId = "other_cars",
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Search.route) {
                PlaceholderScreen(title = "جستجو")
            }
            composable(Screen.Orders.route) {
                PlaceholderScreen(title = "سفارش‌ها")
            }
            composable(Screen.Profile.route) {
                PlaceholderScreen(title = "پروفایل")
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B1220)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp
        )
    }
}
