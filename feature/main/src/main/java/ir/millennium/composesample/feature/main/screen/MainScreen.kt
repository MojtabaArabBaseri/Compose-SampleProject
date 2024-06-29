package ir.millennium.composesample.feature.main.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.Red
import ir.millennium.composesample.core.designsystem.utils.CustomSnackBar
import ir.millennium.composesample.core.model.entity.NavItemState
import ir.millennium.composesample.core.model.entity.TypeLanguage
import ir.millennium.composesample.core.model.entity.TypeTheme
import ir.millennium.composesample.feature.main.Constants.BACK_PRESSED
import ir.millennium.composesample.feature.main.R
import ir.millennium.composesample.feature.main.dialog.QuestionDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navToAboutMeScreen: @Composable () -> Unit,
    navToArticlesScreen: @Composable (snackbarHostState: SnackbarHostState) -> Unit,
    viewModel: MainScreenViewModel
) {

    val snackbarStatus = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val isShowChangeLanguageDialog = rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val items = listOf(
        NavItemState(
            stringResource(id = R.string.profile),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile)),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile))
        ),
        NavItemState(
            stringResource(id = R.string.articles),
            (ImageVector.vectorResource(id = R.drawable.ic_articles)),
            (ImageVector.vectorResource(id = R.drawable.ic_articles))
        )
    )

    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }

//    val isScrolled = if (bottomNavState == 0) {
//        mutableStateOf(homeScreenViewModel.stateLazyColumn.isScrollingUp())
//    } else {
//        mutableStateOf(articleScreenViewModel.stateLazyColumn.isScrollingUp())
//    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
//                visible = isScrolled.value,
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(250)
                ) + fadeIn(animationSpec = tween(250)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(250)
                ) + fadeOut(animationSpec = tween(250))
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    NavigationBar(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        tonalElevation = 0.dp,
                        containerColor = LocalCustomColorsPalette.current.navigationBottomColor
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = bottomNavState == index,
                                onClick = { bottomNavState = index },
                                icon = {
                                    Icon(
                                        imageVector = if (bottomNavState == index) item.selectedIcon else item.unSelectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.title,
                                        fontWeight = FontWeight.Normal,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Red,
                                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                    selectedTextColor = Red,
                                    unselectedTextColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }
            }
        },
    ) { contentPadding ->

        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding(),
                    start = 0.dp,
                    end = 0.dp
                )
                .fillMaxSize()
        ) {
            AnimatedContent(
                targetState = bottomNavState,
                transitionSpec = {
                    slideInHorizontally(
                        animationSpec = tween(250),
                        initialOffsetX = { if (bottomNavState == 0) it else -it }) togetherWith slideOutHorizontally(
                        animationSpec = tween(250),
                        targetOffsetX = { if (bottomNavState == 0) -it else it }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                content = { bottomNavState ->
                    if (bottomNavState == 0) {
                        navToAboutMeScreen()
                    } else {
                        navToArticlesScreen(snackbarHostState)
                    }
                }, label = ""
            )

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
                    IconButton(onClick = { isShowChangeLanguageDialog.value = true }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_language),
                            contentDescription = "Change Language Icon",
                            tint = LocalCustomColorsPalette.current.iconColorPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (viewModel.userPreferencesRepository.statusTheme.first() == TypeTheme.DARK.typeTheme) {
                                viewModel.userPreferencesRepository.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                            } else {
                                viewModel.userPreferencesRepository.setStatusTheme(TypeTheme.DARK.typeTheme)
                            }
                            (context as? Activity)?.recreate()
                        }
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_change_theme),
                            contentDescription = "Change Theme Icon",
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

    if (isShowChangeLanguageDialog.value) {
        QuestionDialog(
            message = stringResource(id = R.string.message_change_language),
            statusDialog = isShowChangeLanguageDialog,
            onClickYes = {
                coroutineScope.launch {
                    delay(50)
                    if (viewModel.userPreferencesRepository.languageApp.first() == TypeLanguage.ENGLISH.typeLanguage) {
                        viewModel.userPreferencesRepository.setLanguageApp(TypeLanguage.PERSIAN.typeLanguage)
                    } else {
                        viewModel.userPreferencesRepository.setLanguageApp(TypeLanguage.ENGLISH.typeLanguage)
                    }
                    (context as? Activity)?.recreate()
                }
            }
        )
    }


    BackHandler {
        whenUserWantToExitApp(
            context,
            coroutineScope,
            snackbarHostState,
            snackbarStatus
        )
    }
}

fun whenUserWantToExitApp(
    context: Context,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    snackbarStatus: MutableState<Boolean>
) {
    if (BACK_PRESSED + 2000 > System.currentTimeMillis()) {
        (context as? Activity)?.finish()
    } else {
        coroutineScope.launch {
            if (snackbarHostState.currentSnackbarData != null) {
                snackbarStatus.value = false
                BACK_PRESSED = 0
                snackbarHostState.currentSnackbarData?.dismiss()
            } else {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.message_when_user_exit_application),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    BACK_PRESSED = System.currentTimeMillis()
}