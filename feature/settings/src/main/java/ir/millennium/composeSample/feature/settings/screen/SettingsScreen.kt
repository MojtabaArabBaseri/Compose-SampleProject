package ir.millennium.composeSample.feature.settings.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.millennium.composeSample.feature.settings.R
import ir.millennium.composesample.core.designsystem.components.NormalTopAppBar
import ir.millennium.composesample.core.designsystem.components.SelectLanguageDropDown
import ir.millennium.composesample.core.designsystem.theme.GrayLight
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.White
import ir.millennium.composesample.core.model.LanguageModel
import ir.millennium.composesample.core.model.TypeLanguage
import ir.millennium.composesample.core.model.TypeTheme

@Composable
fun SettingsScreen(viewModel: SettingsScreenViewModel, onBackPressed: () -> Unit) {

    val context = LocalContext.current

    val scrollSate = rememberScrollState()

    val stateLanguage = viewModel.stateLanguage.collectAsStateWithLifecycle()

    val stateTheme = viewModel.stateTheme.collectAsStateWithLifecycle()

    val languageList = listOf(
        LanguageModel(
            title = stringResource(id = R.string.persian),
            flag = ImageVector.vectorResource(id = R.drawable.iran_flag),
            typeLanguage = TypeLanguage.PERSIAN.typeLanguage,
            selected = stateLanguage.value == TypeLanguage.PERSIAN.typeLanguage
        ),
        LanguageModel(
            title = stringResource(id = R.string.english),
            flag = ImageVector.vectorResource(id = R.drawable.canada_flag),
            typeLanguage = TypeLanguage.ENGLISH.typeLanguage,
            selected = stateLanguage.value == TypeLanguage.ENGLISH.typeLanguage
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollSate)
            .background(MaterialTheme.colorScheme.background)
    ) {
        NormalTopAppBar(
            stringResource(id = R.string.settings),
            ImageVector.vectorResource(R.drawable.ic_back_toolbar),
            onBackPressed,
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.theme),
                color = LocalCustomColorsPalette.current.textColorPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(GrayLight)
                    .padding(6.dp)
            ) {
                IconButton(
                    onClick = { viewModel.setStatusTheme(TypeTheme.LIGHT.typeTheme) },
                    modifier = Modifier
                        .width(70.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (stateTheme.value == TypeTheme.LIGHT.typeTheme) Green else Color.Transparent),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_sun),
                        contentDescription = "Light icon",
                        modifier = Modifier.size(24.dp),
                        tint = if (stateTheme.value == TypeTheme.LIGHT.typeTheme) White else NavyColor
                    )
                }

                IconButton(
                    onClick = { viewModel.setStatusTheme(TypeTheme.DARK.typeTheme) },
                    modifier = Modifier
                        .width(70.dp)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (stateTheme.value == TypeTheme.DARK.typeTheme) Green else Color.Transparent),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_moon),
                        contentDescription = "Dark icon",
                        modifier = Modifier.size(24.dp),
                        tint = if (stateTheme.value == TypeTheme.DARK.typeTheme) White else NavyColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.language),
                color = LocalCustomColorsPalette.current.textColorPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            SelectLanguageDropDown(
                title = languageList.find { it.selected }?.title!!,
                flag = languageList.find { it.selected }?.flag!!,
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GrayLight)
                ) {
                    languageList.forEach { item ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .clickable {
                                    viewModel.setLanguageApp(item.typeLanguage)
                                    (context as? Activity)?.recreate()
                                }
                        ) {

                            Spacer(modifier = Modifier.width(12.dp))

                            Image(
                                imageVector = item.flag,
                                contentDescription = "flag icon",
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = item.title,
                                modifier = Modifier.weight(1f),
                                color = NavyColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}