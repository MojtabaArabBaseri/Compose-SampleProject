package ir.millennium.composesample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.millennium.composesample.feature.splash.navigation.SPLASH_SCREEN_ROUTE

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = SPLASH_SCREEN_ROUTE) {

        authGraph(navController)

        appGraph(navController)

    }
}