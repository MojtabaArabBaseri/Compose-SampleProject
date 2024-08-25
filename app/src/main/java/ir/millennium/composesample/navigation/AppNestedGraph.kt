package ir.millennium.composesample.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ir.millennium.composeSample.feature.settings.navigation.navigateToSettingsScreen
import ir.millennium.composeSample.feature.settings.navigation.settingsScreen
import ir.millennium.composesample.feature.aboutme.screen.AboutMeScreen
import ir.millennium.composesample.feature.aboutme.viewModel.AboutMeScreenViewModel
import ir.millennium.composesample.feature.articles.screen.ArticleScreen
import ir.millennium.composesample.feature.articles.screen.ArticleScreenViewModel
import ir.millennium.composesample.feature.detail_article.navigation.detailArticleScreen
import ir.millennium.composesample.feature.detail_article.navigation.navigateToDetailArticleScreen
import ir.millennium.composesample.feature.main.navigation.Main_SCREEN_ROUTE
import ir.millennium.composesample.feature.main.navigation.mainScreen
import ir.millennium.composesample.feature.splash.navigation.navigateToSplashScreen

fun NavGraphBuilder.appGraph(navController: NavHostController) {

    mainScreen(
        navToAboutMeScreen = {
            val viewModel = hiltViewModel<AboutMeScreenViewModel>()
            AboutMeScreen(viewModel)
        },
        navToArticlesScreen = { snackbarHostState ->
            val viewModel = hiltViewModel<ArticleScreenViewModel>()
            ArticleScreen(
                articleScreenViewModel = viewModel,
                snackbarHostState = snackbarHostState,
                navToDetailArticleScreen = { articleItemJson ->
                    navController.navigateToDetailArticleScreen(articleItemJson)
                })
        },
        navToSplashScreen = { navController.navigateToSplashScreen(Main_SCREEN_ROUTE) },
        navToSettingsScreen = { navController.navigateToSettingsScreen() }
    )

    detailArticleScreen(onBackPressed = { navController.navigateUp() })

    settingsScreen(onBackPressed = { navController.navigateUp() })
}