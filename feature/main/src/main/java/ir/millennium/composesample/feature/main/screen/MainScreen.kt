package ir.millennium.composesample.feature.main.screen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.utils.CustomSnackBar
import ir.millennium.composesample.core.model.BottomNavItemState
import ir.millennium.composesample.feature.main.Constants.BACK_PRESSED
import ir.millennium.composesample.feature.main.R
import ir.millennium.composesample.feature.main.dialog.QuestionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navToAboutMeScreen: @Composable () -> Unit,
    navToArticlesScreen: @Composable (snackbarHostState: SnackbarHostState) -> Unit,
    navToSplashScreen: () -> Unit,
    navToSettingsScreen: () -> Unit,
    viewModel: MainScreenViewModel
) {

    val context = LocalContext.current

    val isShowExitAppDialog = rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarStatus = rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val bottomNavState = rememberSaveable { mutableIntStateOf(0) }

    val bottomNavItemList = listOf(
        BottomNavItemState(
            stringResource(id = R.string.profile),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile)),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile))
        ), BottomNavItemState(
            stringResource(id = R.string.articles),
            (ImageVector.vectorResource(id = R.drawable.ic_articles)),
            (ImageVector.vectorResource(id = R.drawable.ic_articles))
        )
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            DrawerSheet(
                viewModel = viewModel,
                drawerState = drawerState,
                isShowExitAppDialog = isShowExitAppDialog,
                navToSettingsScreen = navToSettingsScreen
            )
        }, drawerState = drawerState
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavItemList, bottomNavState = bottomNavState
                )
            },
        ) { contentPadding ->
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {

                AnimatedContent(
                    targetState = bottomNavState,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(250),
                            initialOffsetX = { if (bottomNavState.intValue == 0) it else -it }) togetherWith slideOutHorizontally(
                            animationSpec = tween(250),
                            targetOffsetX = { if (bottomNavState.intValue == 0) -it else it })
                    },
                    modifier = Modifier.fillMaxSize(),
                    label = ""
                ) { bottomNavState ->
                    if (bottomNavState.intValue == 0) {
                        navToAboutMeScreen()
                    } else {
                        navToArticlesScreen(snackbarHostState)
                    }
                }

                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets(top = 0, bottom = 0),
                    title = {
                        Text(
                            text = stringResource(id = R.string.title_application),
                            color = LocalCustomColorsPalette.current.textColorPrimary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu Icon",
                                tint = LocalCustomColorsPalette.current.iconColorPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LocalCustomColorsPalette.current.toolbarColor
                    )
                )

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                ) { snackbarData ->
                    CustomSnackBar(snackbarData, snackbarStatus)
                }
            }
        }

    }

    if (isShowExitAppDialog.value) {
        QuestionDialog(
            message = stringResource(id = R.string.message_exit_app),
            stateDialog = isShowExitAppDialog,
            onClickYes = {
                viewModel.signOut()
                navToSplashScreen()
            }
        )
    }

    BackHandler {
        whenUserWantToExitApp(
            context, coroutineScope, snackbarHostState, snackbarStatus
        )
    }
}

fun whenUserWantToExitApp(
    context: Context,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    snackbarStatus: MutableState<Boolean>
) {
    val currentTime = System.currentTimeMillis()
    val activity = context as? Activity

    if (currentTime - BACK_PRESSED <= 2000) {
        activity?.finish()
    } else {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.let {
                snackbarStatus.value = false
                it.dismiss()
            } ?: run {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.message_when_user_exit_application),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    BACK_PRESSED = currentTime
}