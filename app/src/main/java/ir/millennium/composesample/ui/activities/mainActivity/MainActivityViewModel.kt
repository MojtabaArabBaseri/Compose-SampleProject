package ir.millennium.composesample.ui.activities.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.TypeLanguage
import ir.millennium.composesample.core.model.TypeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val statusThemeFlow = userPreferencesRepository.stateTheme
    val stateTheme = statusThemeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TypeTheme.LIGHT.typeTheme
    )

    private val languageAppFlow = userPreferencesRepository.languageApp
    val stateLanguage = languageAppFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TypeLanguage.ENGLISH.typeLanguage
    )

    private val _authScreen = MutableStateFlow(true)
    val authScreen: StateFlow<Boolean> = _authScreen.asStateFlow()

    fun onAuthScreen(authScreen: Boolean) {
        this._authScreen.update { authScreen }
    }
}