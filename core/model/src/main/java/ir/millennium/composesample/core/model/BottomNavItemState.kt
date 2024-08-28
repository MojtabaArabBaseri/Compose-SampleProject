package ir.millennium.composesample.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)