package ir.millennium.composesample.core.designsystem.components.dialogs.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.composesample.core.designsystem.components.dialogs.config.MyDialogConfig
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import javax.inject.Inject

class MyDialogAboutMeComponent @Inject constructor() {

    fun createDialog(config: MyDialogConfig.MyDialogAboutMeConfig): IMyDialogComponent {

        return object : IMyDialogComponent {

            @Composable
            override fun Render() {

                rememberSystemUiController().setNavigationBarColor(MaterialTheme.colorScheme.background)

                Column(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    Text(
                        text = config.content,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 30.sp
                    )
                }
            }
        }
    }
}