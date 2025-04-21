package ir.millennium.composesample.feature.aboutme.viewModel

import androidx.compose.foundation.lazy.LazyListState
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.IMyDialogFactory
import kotlinx.coroutines.flow.StateFlow

class FakeAboutMeScreenViewModel(
    override val languageApp: StateFlow<String>,
    override val stateLazyColumn: LazyListState,
    override val myDialogFactory: IMyDialogFactory,
) : IAboutMeScreenViewModel