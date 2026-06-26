package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.repository.FirebaseRepository
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.BrandSelectionScreen
import com.example.ui.screens.CategoryScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.ModelSelectionScreen
import com.example.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object BrandSelection : Screen("brand_selection")
    object ModelSelection : Screen("model_selection/{brandId}") {
        fun createRoute(brandId: String) = "model_selection/$brandId"
    }
    object Category : Screen("category/{brandId}/{modelId}") {
        fun createRoute(brandId: String, modelId: String) = "category/$brandId/$modelId"
    }
    object Search : Screen("search")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Admin : Screen("admin")
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
                    containerColor = Color(0xFF003087),
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
                            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                            label = { Text(text = item.title, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF3B82F6),
                                selectedTextColor = Color(0xFF3B82F6),
                                unselectedIconColor = Color(0xFFA7B1C2),
                                unselectedTextColor = Color(0xFFA7B1C2),
                                indicatorColor = Color(0xFF00236B)
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
                        navController.navigate(Screen.ModelSelection.createRoute("iran_khodro"))
                    },
                    onNavigateToSaipa = {
                        navController.navigate(Screen.ModelSelection.createRoute("saipa"))
                    },
                    onNavigateToOtherCars = {
                        navController.navigate(Screen.ModelSelection.createRoute("other_cars"))
                    }
                )
            }
            composable(Screen.ModelSelection.route) { backStackEntry ->
                val brandId = backStackEntry.arguments?.getString("brandId") ?: "iran_khodro"
                ModelSelectionScreen(
                    brandId = brandId,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToCategory = { bId, mId ->
                        navController.navigate(Screen.Category.createRoute(bId, mId))
                    }
                )
            }
            composable(Screen.Category.route) { backStackEntry ->
                val brandId = backStackEntry.arguments?.getString("brandId") ?: "iran_khodro"
                val modelId = backStackEntry.arguments?.getString("modelId") ?: ""
                CategoryScreen(
                    brandId = brandId,
                    modelId = modelId,
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
                ProfileScreen(
                    onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                    onNavigateToAdmin = { navController.navigate(Screen.Admin.route) }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Admin.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Admin.route) {
                AdminScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToAdmin: () -> Unit
) {
    val repository = remember { FirebaseRepository() }
    var isLoggedIn by remember { mutableStateOf(repository.isLoggedIn()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003087)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoggedIn) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("خوش آمدید، ادمین", color = Color.White, fontSize = 20.sp)
                Button(
                    onClick = onNavigateToAdmin,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                ) {
                    Text("پنل مدیریت", color = Color.White)
                }
                TextButton(onClick = {
                    repository.signOut()
                    isLoggedIn = false
                }) {
                    Text("خروج از حساب", color = Color.Red)
                }
            }
        } else {
            Button(
                onClick = onNavigateToLogin,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
            ) {
                Text("ورود مدیریت", color = Color.White)
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003087)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = Color.White, fontSize = 24.sp)
    }
}
