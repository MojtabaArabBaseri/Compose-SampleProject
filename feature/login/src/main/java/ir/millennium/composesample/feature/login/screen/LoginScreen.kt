package ir.millennium.composesample.feature.login.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.millennium.composesample.core.designsystem.theme.GrayDark
import ir.millennium.composesample.core.designsystem.theme.GrayMedium
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.White
import ir.millennium.composesample.core.model.entity.TypeTheme
import ir.millennium.composesample.feature.login.Constants.PASSWORD
import ir.millennium.composesample.feature.login.Constants.USER_NAME
import ir.millennium.composesample.feature.login.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel, navToMainScreen: () -> Unit) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    var userNameValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var isLoadingVisible by rememberSaveable { mutableStateOf(false) }
    var statusEnabledCardLogin by rememberSaveable { mutableStateOf(true) }
    var isCorrectUserName by rememberSaveable { mutableStateOf(false) }
    var isCorrectPassword by rememberSaveable { mutableStateOf(false) }

    var visibleAnimationEnterScreen by rememberSaveable { mutableStateOf(false) }

    val stateTheme = viewModel.typeTheme.collectAsState()

    fun validateUserName(inputText: String) {
        isCorrectUserName = inputText.lowercase() != USER_NAME
    }

    fun validatePassword(inputText: String) {
        isCorrectPassword = inputText != PASSWORD
    }

    AnimatedVisibility(
        visible = visibleAnimationEnterScreen
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .paint(
                    painterResource(id = if (stateTheme.value == TypeTheme.DARK.typeTheme) R.drawable.background_splash_dark_theme else R.drawable.background_login_light_theme),
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

                Spacer(modifier = Modifier.height(dimensionResource(id = ir.millennium.composesample.core.designsystem.R.dimen.space_logo_from_top)))

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

                OutlinedTextField(
                    value = userNameValue,
                    onValueChange = { newText ->
                        userNameValue = newText
                        validateUserName(userNameValue)

                    },
                    isError = isCorrectUserName,

                    supportingText = {
                        if (isCorrectUserName) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.username_incorrect),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },

                    label = {
                        Text(
                            text = stringResource(id = R.string.user_name),
                            modifier = Modifier.background(Transparent),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Transparent)
                        .padding(start = 55.dp, end = 55.dp)
                        .focusRequester(focusRequester)
                        .animateEnterExit(enter = fadeIn(tween(1000, 1000))),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    placeholder = {
                        Text(
                            text = USER_NAME,
                            modifier = Modifier.background(Transparent),
                            color = GrayDark,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium

                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = (ImageVector.vectorResource(id = R.drawable.ic_mobile)),
                            contentDescription = "Email Icon",
                            tint = LocalCustomColorsPalette.current.iconColorPrimary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),

                    keyboardActions = KeyboardActions {
                        validateUserName(userNameValue)
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LocalCustomColorsPalette.current.textColorPrimary,
                        unfocusedTextColor = GrayDark,
                        focusedPlaceholderColor = LocalCustomColorsPalette.current.textColorPrimary,
                        unfocusedPlaceholderColor = GrayDark,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = Green,
                        unfocusedLabelColor = GrayDark,
                        unfocusedBorderColor = GrayDark,
                        focusedBorderColor = NavyColor,
                        cursorColor = Green
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = { newText ->
                        passwordValue = newText
                        validatePassword(passwordValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Transparent)
                        .padding(start = 55.dp, end = 55.dp)
                        .animateEnterExit(enter = fadeIn(tween(1000, 1500))),
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.background(Transparent)
                        )
                    },
                    isError = isCorrectPassword,
                    supportingText = {
                        if (isCorrectPassword) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.password_incorrect),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = PASSWORD,
                            style = MaterialTheme.typography.bodyMedium,
                            color = GrayMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = (ImageVector.vectorResource(id = R.drawable.ic_code)),
                            contentDescription = "Code Icon",
                            tint = LocalCustomColorsPalette.current.textColorPrimary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LocalCustomColorsPalette.current.textColorPrimary,
                        unfocusedTextColor = GrayDark,
                        focusedPlaceholderColor = LocalCustomColorsPalette.current.textColorPrimary,
                        unfocusedPlaceholderColor = GrayDark,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedLabelColor = Green,
                        unfocusedLabelColor = GrayDark,
                        unfocusedBorderColor = GrayDark,
                        focusedBorderColor = NavyColor,
                        cursorColor = Green
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_btn_regiter_from_lbl_family_sign_up)))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.size_height_button))
                        .padding(start = 55.dp, end = 55.dp)
                        .animateEnterExit(enter = fadeIn(tween(1000, 2000)))
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp))
                        .clickable(enabled = statusEnabledCardLogin) {
                            if (checkFieldForValidation(
                                    userNameValue,
                                    passwordValue,
                                    snackbarHostState,
                                    coroutineScope,
                                    context
                                )
                            ) {
                                if (checkAuthentication(
                                        userNameValue.lowercase(),
                                        passwordValue,
                                        snackbarHostState,
                                        coroutineScope,
                                        context
                                    )
                                ) {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    statusEnabledCardLogin = false
                                    coroutineScope.launch(Dispatchers.IO) {
                                        viewModel.userPreferencesRepository.setStatusLoginUser(true)
                                    }
                                    isLoadingVisible = !isLoadingVisible
                                    coroutineScope.launch {
                                        delay(3000)
                                        isLoadingVisible = !isLoadingVisible
                                        delay(500)
                                        navToMainScreen()
                                    }
                                }
                            }
                        },

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp, pressedElevation = 16.dp
                    ),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Green)
                ) {
                    Crossfade(
                        modifier = Modifier.fillMaxSize(),
                        targetState = isLoadingVisible,
                        animationSpec = tween(400),
                        label = ""
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
                                    modifier = Modifier.wrapContentSize(),
                                    color = NavyColor,
                                    fontWeight = FontWeight.ExtraBold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(70.dp))
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
                hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    LaunchedEffect(coroutineScope) {
        visibleAnimationEnterScreen = true
    }

}

fun checkFieldForValidation(
    userName: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    context: Context
): Boolean {
    return if (userName.isEmpty() || password.isEmpty()) {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(R.string.please_enter_fields)) }
        false
    } else {
        true
    }
}

fun checkAuthentication(
    userName: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    context: Context
) = when {

    (userName == USER_NAME && password == PASSWORD) -> {
        true
    }

    else -> {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(R.string.message_when_username_password_incorrect)) }
        false
    }
}