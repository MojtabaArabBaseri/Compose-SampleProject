package ir.millennium.composesample.feature.splash.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.model.entity.TypeTheme
import ir.millennium.composesample.feature.splash.Constants
import ir.millennium.composesample.feature.splash.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    navToLoginScreen: () -> Unit,
    navToMainScreen: () -> Unit,
    authState: AuthState?
) {

    var isVisibleLogo by rememberSaveable { mutableStateOf(false) }
    val stateTheme = viewModel.typeTheme.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .paint(
                painterResource(id = if (stateTheme.value == TypeTheme.DARK.typeTheme) R.drawable.background_splash_dark_theme else R.drawable.background_splash_light_theme),
                contentScale = ContentScale.FillBounds
            ), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisibleLogo, enter = fadeIn(tween(2500))
        ) {
            Image(
                painter = painterResource(
                    id = if (stateTheme.value == TypeTheme.DARK.typeTheme) {
                        R.drawable.ic_logo_complete_light
                    } else {
                        R.drawable.ic_logo_complete_dark
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(width = 280.dp, height = 150.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        isVisibleLogo = true
        delay(Constants.SPLASH_DISPLAY_LENGTH)
        viewModel.isUserLogin()
    }

    LaunchedEffect(key1 = authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                authState.user?.let { viewModel.saveUserData(it) }
                navToMainScreen()
            }

            is AuthState.Unauthenticated -> {
                navToLoginScreen()
            }

            else -> {}
        }
    }
}