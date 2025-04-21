package ir.millennium.composesample.feature.splash.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.millennium.composesample.core.designsystem.icons.MyIcons
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.model.TypeTheme
import ir.millennium.composesample.core.utils.ui.MultiScreenPreview
import ir.millennium.composesample.feature.splash.Constants
import ir.millennium.composesample.feature.splash.viewModel.FakeSplashScreenViewModel
import ir.millennium.composesample.feature.splash.viewModel.ISplashScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

@Composable
fun SplashScreen(
    viewModel: ISplashScreenViewModel, navToLoginScreen: () -> Unit, navToMainScreen: () -> Unit
) {


    val authState by viewModel.authState.collectAsStateWithLifecycle()

    val stateTheme by viewModel.typeTheme.collectAsStateWithLifecycle()

    var animatedState by remember { mutableStateOf(false) }

    val scaleLogo by animateFloatAsState(
        targetValue = if (animatedState) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000, easing = { OvershootInterpolator(1.4f).getInterpolation(it) }),
        label = "scale animation on logo"
    )

    val translationY by animateFloatAsState(
        targetValue = if (animatedState) 0f else -100f,
        animationSpec = tween(durationMillis = 1000),
        label = "translate animation on logo"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .paint(
                painterResource(
                    id = if (stateTheme == TypeTheme.DARK.typeTheme)
                        MyIcons.BackgroundAuthenticationDark
                    else
                        MyIcons.BackgroundAuthenticationLight
                ),
                contentScale = ContentScale.FillBounds,
            ), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = if (stateTheme == TypeTheme.DARK.typeTheme) {
                    MyIcons.LogoCompleteLight
                } else {
                    MyIcons.LogoCompleteDark
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .size(width = 280.dp, height = 150.dp)
                .scale(scaleLogo)
                .offset { IntOffset(0, translationY.roundToInt()) })
    }

    LaunchedEffect(Unit) {
        animatedState = true
        delay(Constants.SPLASH_DISPLAY_LENGTH)
        viewModel.isUserLogin()
    }

    LaunchedEffect(key1 = authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                (authState as AuthState.Authenticated).user?.let { viewModel.saveUserData(it) }
                navToMainScreen()
            }

            is AuthState.Unauthenticated -> {
                navToLoginScreen()
            }

            else -> {}
        }
    }
}

@MultiScreenPreview
@Composable
fun SplashScreenPreview() {

    val viewModel = FakeSplashScreenViewModel(
        MutableStateFlow(TypeTheme.LIGHT.typeTheme), MutableStateFlow(null)
    )

    SplashScreen(viewModel = viewModel, navToLoginScreen = {}, navToMainScreen = {})
}