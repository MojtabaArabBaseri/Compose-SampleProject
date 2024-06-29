package ir.millennium.composesample.core.model.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItemState(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)