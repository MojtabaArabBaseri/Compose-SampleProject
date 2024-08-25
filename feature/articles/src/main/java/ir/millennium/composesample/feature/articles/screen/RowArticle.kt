package ir.millennium.composesample.feature.articles.screen

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import ir.millennium.composesample.core.designsystem.theme.GrayDark
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.network.model.ArticleItem
import ir.millennium.composesample.feature.articles.R


@Composable
fun rowArticle(
    articleItem: ArticleItem,
    navToDetailArticleScreen: (articleItemJson: String) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)))
            .clickable {
                val articleItemJson = Uri.encode(Gson().toJson(articleItem))
                navToDetailArticleScreen(articleItemJson)
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)),
        colors = CardDefaults.cardColors(containerColor = LocalCustomColorsPalette.current.rowSocialNetworkBackground)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            ConstraintLayout(modifier = Modifier.wrapContentSize()) {
                val (imageRef, titleRef, descriptionRef, dateRef) = createRefs()

                AsyncImage(
                    model = articleItem.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.matchParent
                            height = Dimension.value(150.dp)
                        }
                        .fillMaxWidth()
                        .aspectRatio(21f / 9f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)))
                        .border(
                            0.dp,
                            Color.Transparent,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
                        ),
                    contentScale = ContentScale.Crop
                )

                articleItem.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(titleRef) {
                                top.linkTo(imageRef.bottom)
                                start.linkTo(parent.start)
                            }
                            .padding(start = 8.dp, end = 8.dp),
                        fontWeight = FontWeight.Bold,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                articleItem.author?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(descriptionRef) {
                                top.linkTo(titleRef.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(dateRef.start)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
                        color = GrayDark,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                articleItem.publishedAt?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(dateRef) {
                                top.linkTo(titleRef.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 12.dp, end = 8.dp, bottom = 16.dp),
                        color = GrayDark,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}