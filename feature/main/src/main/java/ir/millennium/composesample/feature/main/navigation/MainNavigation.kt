package ir.millennium.composesample.feature.main.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.millennium.composesample.feature.main.screen.MainScreen
import ir.millennium.composesample.feature.main.screen.MainScreenViewModel

const val Main_SCREEN_ROUTE = "MainScreen"

fun NavGraphBuilder.mainScreen(
    navToAboutMeScreen: @Composable () -> Unit,
    navToArticlesScreen: @Composable (snackbarHostState: SnackbarHostState) -> Unit,
    navToSplashScreen: () -> Unit,
    navToSettingsScreen: () -> Unit,
    enterTransient: () -> EnterTransition = {
        fadeIn(tween(500))
//        slideInHorizontally(
//            initialOffsetX = { it / 2 }, animationSpec = tween(500)
//        )
    },
    exitTransient: () -> ExitTransition = {
        fadeOut(tween(500))
//        slideOutHorizontally(
//            targetOffsetX = { it / 2 }, animationSpec = tween(500)
//        )
    }
) {

    composable(route = Main_SCREEN_ROUTE,
        enterTransition = { enterTransient() },
        exitTransition = { exitTransient() }) {
        val viewModel = hiltViewModel<MainScreenViewModel>(it)
        MainScreen(
            navToAboutMeScreen,
            navToArticlesScreen,
            navToSplashScreen,
            navToSettingsScreen,
            viewModel
        )
    }
}

fun NavController.navigateToMainScreen(parentRoute: String) {
    navigate(Main_SCREEN_ROUTE) {
        popUpTo(parentRoute) { inclusive = true }
    }
}