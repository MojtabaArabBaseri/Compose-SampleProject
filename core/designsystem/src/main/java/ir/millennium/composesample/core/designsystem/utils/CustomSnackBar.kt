package ir.millennium.composesample.core.designsystem.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.Red
import kotlinx.coroutines.delay

@Composable
fun CustomSnackBar(
    snackbarData: SnackbarData,
    snackbarStatus: MutableState<Boolean>
) {

    LaunchedEffect(Unit) {

        snackbarStatus.value = true

        when (snackbarData.visuals.duration.ordinal) {
            SnackbarDuration.Short.ordinal -> {
                delay(3700)
                snackbarData.dismiss()
                snackbarStatus.value = false
            }

            SnackbarDuration.Long.ordinal -> {
                delay(9700)
                snackbarData.dismiss()
                snackbarStatus.value = false
            }

            SnackbarDuration.Indefinite.ordinal -> {

            }
        }
    }

    AnimatedVisibility(
        visible = snackbarStatus.value,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(150)
        ) + fadeIn(animationSpec = tween(150)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(150)
        ) + fadeOut(animationSpec = tween(150))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .defaultMinSize(minHeight = 65.dp)
                .background(Green)
                .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp)
        ) {
            val (TextRef, ButtonRef) = createRefs()

            Text(
                text = snackbarData.visuals.message, modifier = Modifier
                    .constrainAs(TextRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(
                            if (snackbarData.visuals.actionLabel != null) ButtonRef.start else parent.end,
                            8.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                color = NavyColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            if (snackbarData.visuals.actionLabel != null) {
                TextButton(
                    onClick = {
                        snackbarData.performAction()
                        snackbarStatus.value = false
                    },
                    modifier = Modifier.constrainAs(ButtonRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    }
                ) {
                    snackbarData.visuals.actionLabel?.let {
                        Text(
                            text = it,
                            color = Red,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}