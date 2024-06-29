package ir.millennium.composesample.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ir.millennium.composesample.feature.aboutme.screen.AboutMeScreen
import ir.millennium.composesample.feature.aboutme.screen.AboutMeScreenViewModel
import ir.millennium.composesample.feature.articles.screen.ArticleScreen
import ir.millennium.composesample.feature.articles.screen.ArticleScreenViewModel
import ir.millennium.composesample.feature.detail_article.navigation.detailArticleScreen
import ir.millennium.composesample.feature.detail_article.navigation.navigateToDetailArticleScreen
import ir.millennium.composesample.feature.main.navigation.mainScreen

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
        })

    detailArticleScreen(onBackPressed = { navController.navigateUp() })
}