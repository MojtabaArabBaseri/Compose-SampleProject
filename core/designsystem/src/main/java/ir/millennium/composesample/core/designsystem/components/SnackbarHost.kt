package ir.millennium.composesample.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.millennium.composesample.core.designsystem.theme.Green
import ir.millennium.composesample.core.designsystem.theme.NavyColor
import ir.millennium.composesample.core.designsystem.theme.Red

@Composable
fun MySnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
    containerColor: Color = Green,
    messageColor: Color = NavyColor,
    labelColor: Color = Red,
    snackbar: @Composable (() -> Unit)? = null,
) {
    SnackbarHost(
        hostState = snackbarHostState, modifier = modifier, snackbar = { data ->
            if (snackbar != null) snackbar()
            else MySnackbar(
                data = data,
                maxLines = maxLines,
                containerColor = containerColor,
                messageColor = messageColor,
                labelColor = labelColor
            )
        })
}

@Composable
fun MySnackbar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
    shape: Shape = SnackbarDefaults.shape,
    maxLines: Int = 2,
    containerColor: Color = Green,
    messageColor: Color = NavyColor,
    labelColor: Color = Red,
) {
    Snackbar(
        modifier = modifier, shape = shape, containerColor = containerColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.visuals.message,
                maxLines = maxLines,
                color = messageColor,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            data.visuals.actionLabel?.let { label ->
                TextButton(
                    onClick = { data.performAction() },
                    modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                ) {
                    Text(
                        text = label, color = labelColor
                    )
                }
            }
        }
    }
}