package ir.millennium.composesample.feature.aboutme.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.model.UserProfileSocialNetworkEntity
import ir.millennium.composesample.feature.aboutme.R

@Composable
fun RowSocialNetwork(item: UserProfileSocialNetworkEntity) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.max_height_editText))
            .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)))
            .clickable { navToSocialNetwork(context, item) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)),
        colors = CardDefaults.cardColors(containerColor = LocalCustomColorsPalette.current.rowSocialNetworkBackground)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lblNameSocialNetworkRef, ibArrowRef) = createRefs()
            Text(
                modifier = Modifier
                    .constrainAs(lblNameSocialNetworkRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp),
                text = stringResource(id = item.title),
                color = LocalCustomColorsPalette.current.textColorPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                onClick = { navToSocialNetwork(context, item) },
                modifier = Modifier
                    .constrainAs(ibArrowRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "",
                    tint = LocalCustomColorsPalette.current.textColorPrimary,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        }
    }
//    }
}

fun navToSocialNetwork(context: Context, item: UserProfileSocialNetworkEntity) {
    ContextCompat.startActivity(
        context, Intent(Intent.ACTION_VIEW, Uri.parse(context.resources.getString(item.link))),
        null
    )
}


