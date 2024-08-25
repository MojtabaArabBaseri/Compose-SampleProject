package ir.millennium.composeSample.feature.settings.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ir.millennium.composeSample.feature.settings.screen.SettingsScreen
import ir.millennium.composeSample.feature.settings.screen.SettingsScreenViewModel

const val SETTINGS_SCREEN_ROUTE = "SettingsScreen"

fun NavGraphBuilder.settingsScreen(
    onBackPressed: () -> Unit, enterTransient: () -> EnterTransition = {
        fadeIn(tween(500))
    }, exitTransient: () -> ExitTransition = {
        fadeOut(tween(500))
    }
) {

    composable(route = SETTINGS_SCREEN_ROUTE,
        enterTransition = { enterTransient() },
        exitTransition = { exitTransient() }) { backstackEntry ->
        val viewModel = hiltViewModel<SettingsScreenViewModel>(backstackEntry)
        SettingsScreen(viewModel,onBackPressed)
    }
}

fun NavController.navigateToSettingsScreen() {
    navigate(SETTINGS_SCREEN_ROUTE)
}