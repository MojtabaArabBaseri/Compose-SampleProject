package ir.millennium.composesample.feature.login.screen

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.White
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.firebase.authentication.SignInResult
import ir.millennium.composesample.core.model.entity.TypeTheme
import ir.millennium.composesample.feature.login.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel, navToMainScreen: () -> Unit, authState: AuthState?
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isLoadingVisible by rememberSaveable { mutableStateOf(false) }
    var visibleAnimationEnterScreen by rememberSaveable { mutableStateOf(false) }
    var elevationButton by remember { mutableStateOf(0.dp) }
    val enableButton = remember { derivedStateOf { !isLoadingVisible } }

    val stateTheme by viewModel.typeTheme.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            isLoadingVisible = false
            if (result.resultCode == RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = viewModel.googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    signInResult.data?.let { viewModel.saveDataUser(it) }
                    viewModel.onSignInResult(signInResult)
                }
            } else if (result.resultCode == RESULT_CANCELED) {
                viewModel.onSignInResult(
                    SignInResult(
                        null, errorMessage = CancellationException("Sign in Cancelled")
                    )
                )
            }
        })

    AnimatedVisibility(visible = visibleAnimationEnterScreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .paint(
                    painterResource(
                        id = if (stateTheme == TypeTheme.DARK.typeTheme) R.drawable.background_splash_dark_theme
                        else R.drawable.background_login_light_theme
                    ),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_logo_from_top)))

                Text(
                    text = stringResource(id = R.string.title_application),
                    modifier = Modifier
                        .wrapContentSize()
                        .animateEnterExit(enter = fadeIn(tween(1000))),
                    color = Green,
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    letterSpacing = 2.sp
                )

                Text(
                    text = stringResource(id = R.string.description_application),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 28.dp, end = 28.dp)
                        .animateEnterExit(enter = fadeIn(tween(1000, 500))),
                    color = Green,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_box_username_from_logo)))

                Text(
                    text = stringResource(id = R.string.description_for_login),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 40.dp, end = 40.dp)
                        .animateEnterExit(enter = fadeIn(tween(1000, 1000))),
                    color = LocalCustomColorsPalette.current.textColorPrimary,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_btn_regiter_from_lbl_family_sign_up)))

                Button(
                    onClick = {
                        isLoadingVisible = true
                        coroutineScope.launch {
                            viewModel.resetState()
                            val signInIntentSender = viewModel.googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(signInIntentSender ?: return@launch)
                                    .build()
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.size_height_button))
                        .padding(start = 55.dp, end = 55.dp)
                        .shadow(elevationButton, RoundedCornerShape(28.dp))
                        .animateEnterExit(enter = fadeIn(tween(1000, 1500))),
                    enabled = enableButton.value,
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        disabledContainerColor = Green
                    )
                ) {
                    Crossfade(
                        modifier = Modifier.fillMaxSize(),
                        targetState = isLoadingVisible,
                        animationSpec = tween(400),
                        label = "runAnimButton"
                    ) { isLoadingVisible ->
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            if (isLoadingVisible) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(30.dp),
                                    strokeWidth = 4.dp,
                                    color = NavyColor,
                                    strokeCap = StrokeCap.Round
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.login),
                                    color = NavyColor,
                                    fontWeight = FontWeight.ExtraBold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.copyright),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .animateEnterExit(enter = fadeIn(tween(1000, 2500))),
                style = MaterialTheme.typography.bodySmall,
                color = White,
                textAlign = TextAlign.Right
            )

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    LaunchedEffect(Unit) {
        visibleAnimationEnterScreen = true
        delay(2000)
        elevationButton = 8.dp
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> navToMainScreen()

            is AuthState.Error -> {
                authState.exception?.localizedMessage?.let {
                    snackbarHostState.showSnackbar(
                        it
                    )
                }
            }

            else -> {}
        }
    }
}
