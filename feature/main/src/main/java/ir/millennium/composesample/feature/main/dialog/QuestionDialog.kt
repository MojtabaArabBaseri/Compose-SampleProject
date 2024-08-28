package ir.millennium.composesample.feature.main.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.White
import ir.millennium.composesample.feature.main.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDialog(
    title: String = stringResource(id = R.string.attention),
    message: String = "ad",
    labelYesButton: String = stringResource(id = R.string.yes),
    labelNoButton: String = stringResource(id = R.string.no),
    stateDialog: MutableState<Boolean>,
    onClickYes: () -> Unit = {},
    onClickNo: () -> Unit = {}
) {
    BasicAlertDialog(
        onDismissRequest = {
            stateDialog.value = false
        }, modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {

                val (imageTitleRef, titleTextRef, messageRef, yesButtonRef, noButtonRef) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageTitleRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 8.dp),
                )

                Text(
                    modifier = Modifier
                        .constrainAs(titleTextRef) {
                            top.linkTo(imageTitleRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = LocalCustomColorsPalette.current.textColorPrimary,
                )

                Text(modifier = Modifier
                    .constrainAs(messageRef) {
                        top.linkTo(titleTextRef.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalCustomColorsPalette.current.textColorPrimary)

                Button(onClick = {
                    stateDialog.value = false
                    onClickYes()
                },
                    modifier = Modifier
                        .constrainAs(yesButtonRef) {
                            top.linkTo(messageRef.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 74.dp, end = 16.dp, bottom = 24.dp)
                        .defaultMinSize(minWidth = 90.dp),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_button)),
                    colors = ButtonDefaults.buttonColors(containerColor = Green)) {
                    Text(
                        text = labelYesButton,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                OutlinedButton(onClick = {
                    stateDialog.value = false
                    onClickNo()
                },
                    modifier = Modifier
                        .constrainAs(noButtonRef) {
                            top.linkTo(messageRef.bottom)
                            end.linkTo(yesButtonRef.start)
                        }
                        .padding(top = 74.dp, end = 12.dp, bottom = 24.dp)
                        .defaultMinSize(minWidth = 90.dp),
                    border = BorderStroke(0.dp, Color.Transparent),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_button))) {
                    Text(
                        text = labelNoButton,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}