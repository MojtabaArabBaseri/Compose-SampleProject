package ir.millennium.composesample.feature.aboutme.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.feature.aboutme.R

@Preview
@Composable
fun AboutMeDialog() {

    rememberSystemUiController().setNavigationBarColor(MaterialTheme.colorScheme.background)

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.about_me_detail),
            color = LocalCustomColorsPalette.current.textColorPrimary,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 30.sp
        )
    }
}