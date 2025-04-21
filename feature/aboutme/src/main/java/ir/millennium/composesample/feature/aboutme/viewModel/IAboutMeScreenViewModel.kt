package ir.millennium.composesample.feature.aboutme.viewModel

import androidx.compose.foundation.lazy.LazyListState
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.IMyDialogFactory
import kotlinx.coroutines.flow.StateFlow

interface IAboutMeScreenViewModel {

    val languageApp: StateFlow<String>

    val stateLazyColumn: LazyListState

    val myDialogFactory: IMyDialogFactory
}