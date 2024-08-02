package ir.millennium.composesample.feature.login.navigation

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
import ir.millennium.composesample.feature.login.screen.LoginScreen
import ir.millennium.composesample.feature.login.screen.LoginScreenViewModel

const val LOGIN_SCREEN_ROUTE = "LoginScreen"

fun NavGraphBuilder.loginScreen(
    navToMainScreen: () -> Unit,
    enterTransient: () -> EnterTransition = {
        slideInHorizontally(
            initialOffsetX = { it / 2 }, animationSpec = tween(500)
        )
    },
    exitTransient: () -> ExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { it / 2 }, animationSpec = tween(500)
        )
    }
) {

    composable(route = LOGIN_SCREEN_ROUTE,
        enterTransition = { enterTransient() },
        exitTransition = { exitTransient() }
    ) {
        val viewModel = hiltViewModel<LoginScreenViewModel>(it)
        val authState by viewModel.authState.collectAsStateWithLifecycle()
        LoginScreen(
            viewModel = viewModel,
            navToMainScreen = navToMainScreen,
            authState = authState
        )
    }
}

fun NavController.navigateToLoginScreen(parentRoute: String) {
    navigate(LOGIN_SCREEN_ROUTE) {
        popUpTo(parentRoute) { inclusive = true }
    }
}