package ir.millennium.composesample.core.designsystem.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette

@Composable
fun NormalTopAppBar(
    title: String,
    icon: ImageVector,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(
            top = 0, bottom = 0
        ),
        title = {
            Text(
                text = title,
                color = LocalCustomColorsPalette.current.textColorPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Back Icon",
                    tint = LocalCustomColorsPalette.current.iconColorPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.toolbarColor
        )
    )
}