package ir.millennium.composesample.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class LanguageModel(
    val title: String,
    val flag: ImageVector,
    val typeLanguage: String,
    val selected: Boolean
)
