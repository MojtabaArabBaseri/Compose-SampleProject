package ir.millennium.composesample.core.designsystem.components.dialogs.config

import androidx.compose.runtime.MutableState

sealed class MyDialogConfig {

    data class MyDialogQuestionConfig(
        val title: String,
        val message: String,
        val labelYesButton: String,
        val labelNoButton: String,
        val stateDialog: MutableState<Boolean>,
        val onClickYes: () -> Unit = {},
        val onClickNo: () -> Unit = {}
    ) : MyDialogConfig()

    data class MyDialogAboutMeConfig(
        val content: String
    ) : MyDialogConfig()
}
