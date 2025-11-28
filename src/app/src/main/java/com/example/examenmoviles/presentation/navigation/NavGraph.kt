package com.example.examenmoviles.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examenmoviles.presentation.screens.CountryDetailScreen
import com.example.examenmoviles.presentation.screens.HomeScreen

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Detail : Screen("country/{name}") {
        fun createRoute(name: String) = "country/$name"
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun CountryCovidNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCountryClick = { name ->
                    navController.navigate(Screen.Detail.createRoute(name))
                },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "1"
            CountryDetailScreen(
                viewModel = hiltViewModel(),
                countryName = name,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
