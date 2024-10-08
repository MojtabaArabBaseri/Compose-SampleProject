package ir.millennium.composesample.core.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ir.millennium.composesample.core.designsystem.theme.GrayMedium
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette

@Composable
fun SelectLanguageDropDown(
    title: String,
    flag: ImageVector,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember { mutableStateOf(initiallyOpened) }

    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        ), label = ""
    )

    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        ), label = ""
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Transparent)
                .border(1.dp, GrayMedium, RoundedCornerShape(8.dp))
                .padding(6.dp)
        ) {

            Spacer(modifier = Modifier.width(12.dp))

            Image(
                imageVector = flag,
                contentDescription = "flag icon",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = LocalCustomColorsPalette.current.textColorPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(
                onClick = { isOpen = !isOpen },
                modifier = Modifier
                    .scale(1f, scaleY = if (isOpen) -1f else 1f)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Open or close the drop down",
                    tint = LocalCustomColorsPalette.current.iconColorPrimary,
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}