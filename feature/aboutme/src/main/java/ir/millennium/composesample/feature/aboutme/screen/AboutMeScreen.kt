package ir.millennium.composesample.feature.aboutme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.composesample.core.designsystem.components.MyStandardButton
import ir.millennium.composesample.core.designsystem.components.dialogs.component.IMyDialogComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.config.MyDialogConfig
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.IMyDialogFactory
import ir.millennium.composesample.core.designsystem.theme.AppFont
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.White
import ir.millennium.composesample.core.model.TypeLanguage
import ir.millennium.composesample.core.utils.ui.MultiScreenPreview
import ir.millennium.composesample.feature.aboutme.Constants.USER_PROFILE_DATA
import ir.millennium.composesample.feature.aboutme.R
import ir.millennium.composesample.feature.aboutme.viewModel.FakeAboutMeScreenViewModel
import ir.millennium.composesample.feature.aboutme.viewModel.IAboutMeScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(
    viewModel: IAboutMeScreenViewModel
) {

    val modalBottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val localCustomColorsPalette = LocalCustomColorsPalette.current

    val languageApp by viewModel.languageApp.collectAsStateWithLifecycle()

    LazyColumn(
        state = viewModel.stateLazyColumn,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {

        item {
            ConstraintLayout {
                val (imageRef, textRef) = createRefs()

                Image(
                    painter = painterResource(USER_PROFILE_DATA.image),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                        height = Dimension.value(300.dp)
                    },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = stringResource(id = USER_PROFILE_DATA.fullName),
                    modifier = Modifier.constrainAs(textRef) {
                        top.linkTo(imageRef.bottom)
                        bottom.linkTo(imageRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontFamily = if (languageApp == TypeLanguage.PERSIAN.typeLanguage) AppFont.FontPersian else AppFont.FontEnglish,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 1.sp,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        shadow = Shadow(
                            offset = Offset(-3f, -3f),
                            color = LocalCustomColorsPalette.current.supplementTextColorPrimary
                        ),
                    ),
                )
            }
        }

        items(items = USER_PROFILE_DATA.socialNetwork) { item ->
            RowSocialNetwork(item)
        }

        item {
            MyStandardButton(
                onClick = { isSheetOpen = true },
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                text = {
                    Text(
                        text = stringResource(id = R.string.aboutMe),
                        color = White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                systemUiController.setNavigationBarColor(localCustomColorsPalette.navigationBottomColor)
                isSheetOpen = false
            },
            sheetState = modalBottomSheetState,
            containerColor = MaterialTheme.colorScheme.background,
            scrimColor = NavyColor.copy(alpha = 0.2f),
            tonalElevation = 0.dp,
        ) {
            viewModel.myDialogFactory.createDialog(
                MyDialogConfig.MyDialogAboutMeConfig(stringResource(R.string.about_me_detail))
            ).Render()
        }
    }
}

@MultiScreenPreview
@Composable
fun AboutMeScreenPreview() {
    AboutMeScreen(
        viewModel = FakeAboutMeScreenViewModel(
            MutableStateFlow(TypeLanguage.PERSIAN.typeLanguage),
            LazyListState(),
            FakeIMyDialogFactory()
        )
    )
}

class FakeIMyDialogFactory : IMyDialogFactory {
    override fun createDialog(myDialogConfig: MyDialogConfig): IMyDialogComponent {
        TODO("Not yet implemented")
    }

}