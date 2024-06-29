package ir.millennium.composesample.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ir.millennium.composesample.feature.login.navigation.LOGIN_SCREEN_ROUTE
import ir.millennium.composesample.feature.login.navigation.loginScreen
import ir.millennium.composesample.feature.login.navigation.navigateToLoginScreen
import ir.millennium.composesample.feature.main.navigation.navigateToMainScreen
import ir.millennium.composesample.feature.splash.navigation.SPLASH_SCREEN_ROUTE
import ir.millennium.composesample.feature.splash.navigation.splashScreen

fun NavGraphBuilder.authGraph(navController: NavController) {

    splashScreen(
        navToLoginScreen = { navController.navigateToLoginScreen(parentRoute = SPLASH_SCREEN_ROUTE) },
        navToMainScreen = { navController.navigateToMainScreen(parentRoute = SPLASH_SCREEN_ROUTE) }
    )

    loginScreen(
        navToMainScreen = { navController.navigateToMainScreen(parentRoute = LOGIN_SCREEN_ROUTE) }
    )
}