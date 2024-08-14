package ir.millennium.composesample.feature.aboutme.viewModel

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.StateFlow

class FakeAboutMeScreenViewModel(
    override val languageApp: StateFlow<String>,
    override val stateLazyColumn: LazyListState
) : IAboutMeScreenViewModel