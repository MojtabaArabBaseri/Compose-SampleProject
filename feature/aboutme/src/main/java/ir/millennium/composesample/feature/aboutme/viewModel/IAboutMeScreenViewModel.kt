package ir.millennium.composesample.feature.aboutme.viewModel

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.coroutines.flow.StateFlow

interface IAboutMeScreenViewModel {

    val languageApp: StateFlow<String>

    val stateLazyColumn: LazyListState
}