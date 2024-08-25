package ir.millennium.composesample.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerNavItemState(
    val drawerItem: DrawerItem,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

enum class DrawerItem {
    SETTINGS, EXIT_TO_APP;
}