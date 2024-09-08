package ir.millennium.composeSample.feature.settings.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.TypeLanguage
import ir.millennium.composesample.core.model.TypeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SettingsScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
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

    fun setStatusTheme(typeTheme: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.setStatusTheme(typeTheme)
        }
    }

    fun setLanguageApp(typeLanguage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.setLanguageApp(typeLanguage)
        }
    }
}