package ir.millennium.composesample.feature.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.millennium.composesample.core.designsystem.theme.LocalCustomColorsPalette
import ir.millennium.composesample.core.designsystem.theme.Red
import ir.millennium.composesample.core.model.BottomNavItemState

@Composable
fun BottomNavigationBar(items: List<BottomNavItemState>, bottomNavState: MutableIntState) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        BottomAppBar(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            tonalElevation = 0.dp,
            containerColor = LocalCustomColorsPalette.current.navigationBottomColor,
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = bottomNavState.intValue == index
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { bottomNavState.intValue = index },
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Red,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                        selectedTextColor = Red,
                        unselectedTextColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}
