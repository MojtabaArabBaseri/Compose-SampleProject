package ir.millennium.composesample.feature.aboutme.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.TypeLanguage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class AboutMeScreenViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel(), IAboutMeScreenViewModel {

    override val stateLazyColumn = LazyListState()

    private val languageAppFlow = userPreferencesRepository.languageApp
    override val languageApp = languageAppFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TypeLanguage.ENGLISH.typeLanguage
    )
}

