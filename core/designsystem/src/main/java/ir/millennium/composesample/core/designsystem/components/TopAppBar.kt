package ir.millennium.composesample.core.designsystem.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ir.millennium.composesample.core.designsystem.R
import ir.millennium.composesample.core.designsystem.icons.MyIcons
import ir.millennium.composesample.core.designsystem.theme.AppTheme
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette

@Composable
fun MyTopAppBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCenterAlignedTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun MyTopAppBarPreview() {
    AppTheme {
        MyCenterAlignedTopAppBar(
            title = stringResource(R.string.app_name),
            navigationIcon = MyIcons.Search,
            navigationIconContentDescription = "Navigation icon",
            actionIcon = MyIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}