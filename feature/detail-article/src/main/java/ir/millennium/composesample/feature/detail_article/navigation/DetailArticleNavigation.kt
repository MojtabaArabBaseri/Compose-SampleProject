package ir.millennium.composesample.feature.detail_article.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ir.millennium.composesample.core.network.model.ArticleItem
import ir.millennium.composesample.feature.detail_article.screen.DetailArticleScreen

const val DETAIL_ARTICLE_SCREEN_ROUTE = "DetailArticleScreen"

fun NavGraphBuilder.detailArticleScreen(
    onBackPressed: () -> Unit, enterTransient: () -> EnterTransition = {
        fadeIn(tween(500))
    }, exitTransient: () -> ExitTransition = {
        fadeOut(tween(500))
    }
) {

    composable(route = "$DETAIL_ARTICLE_SCREEN_ROUTE/{detail-article-Item}",
        arguments = listOf(
            navArgument("detail-article-Item") {
                type = NavType.StringType
            },
        ),
        enterTransition = { enterTransient() },
        exitTransition = { exitTransient() }) { backstackEntry ->
        val articleItemJson = backstackEntry.arguments?.getString("detail-article-Item")
        val articleItem = Gson().fromJson(articleItemJson, ArticleItem::class.java)
        DetailArticleScreen(articleItem, onBackPressed)
    }
}

fun NavController.navigateToDetailArticleScreen(articleItemJson: String) {
    navigate("$DETAIL_ARTICLE_SCREEN_ROUTE/$articleItemJson")
}