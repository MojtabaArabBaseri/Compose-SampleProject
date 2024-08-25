package ir.millennium.composesample.feature.splash.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.millennium.composesample.feature.splash.screen.SplashScreen
import ir.millennium.composesample.feature.splash.viewModel.SplashScreenViewModel

const val SPLASH_SCREEN_ROUTE = "SplashScreen"

fun NavGraphBuilder.splashScreen(
    navToLoginScreen: () -> Unit,
    navToMainScreen: () -> Unit,
    enterTransient: () -> EnterTransition = { slideInHorizontally(animationSpec = tween(500)) },
    exitTransient: () -> ExitTransition = { slideOutHorizontally(animationSpec = tween(500)) }
) {

    composable(route = SPLASH_SCREEN_ROUTE,
        enterTransition = { enterTransient() },
        exitTransition = { exitTransient() }) {

        val viewModel = hiltViewModel<SplashScreenViewModel>(it)

        SplashScreen(
            viewModel = viewModel,
            navToLoginScreen = navToLoginScreen,
            navToMainScreen = navToMainScreen
        )
    }
}

fun NavController.navigateToSplashScreen(parentRoute: String) {
    navigate(SPLASH_SCREEN_ROUTE) {
        popUpTo(parentRoute) {
            inclusive = true
        }
    }
}