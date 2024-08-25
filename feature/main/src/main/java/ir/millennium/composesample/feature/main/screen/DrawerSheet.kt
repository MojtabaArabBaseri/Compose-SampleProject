package ir.millennium.composesample.feature.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.Red
import ir.millennium.composesample.core.model.DrawerItem
import ir.millennium.composesample.core.model.DrawerNavItemState
import ir.millennium.composesample.feature.main.R
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(
    viewModel: MainScreenViewModel,
    drawerState: DrawerState,
    isShowExitAppDialog: MutableState<Boolean>,
    navToSettingsScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(-1) }
    val stateUserData = viewModel.stateUserData.collectAsStateWithLifecycle()

    val drawerNavItemList = listOf(
        DrawerNavItemState(
            drawerItem = DrawerItem.SETTINGS,
            title = stringResource(id = R.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
        DrawerNavItemState(
            drawerItem = DrawerItem.EXIT_TO_APP,
            title = stringResource(id = R.string.exitToApp),
            selectedIcon = Icons.AutoMirrored.Filled.ExitToApp,
            unselectedIcon = Icons.AutoMirrored.Outlined.ExitToApp
        )
    )

    ModalDrawerSheet(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
            .navigationBarsPadding(),
        drawerContainerColor = MaterialTheme.colorScheme.background,
        drawerShape = RoundedCornerShape(topEnd = 80.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Red.copy(alpha = 0.3f))
        ) {
            AsyncImage(
                model = stateUserData.value?.profilePictureUrl?.replace("s96-c", "s512-c"),
                contentDescription = "User Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            stateUserData.value?.username?.let { username ->
                Text(
                    text = username,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
                    color = LocalCustomColorsPalette.current.textColorPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 1.sp,
                        shadow = Shadow(
                            offset = Offset(-3f, -3f),
                            color = LocalCustomColorsPalette.current.supplementTextColorPrimary
                        )
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        drawerNavItemList.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = {
                    Text(
                        text = item.title,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    when (item.drawerItem) {
                        DrawerItem.SETTINGS -> navToSettingsScreen()
                        DrawerItem.EXIT_TO_APP -> isShowExitAppDialog.value = true
                    }
                    coroutineScope.launch { drawerState.close() }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.background,
                    selectedContainerColor = Red.copy(0.3f)
                )
            )
        }
    }
}
