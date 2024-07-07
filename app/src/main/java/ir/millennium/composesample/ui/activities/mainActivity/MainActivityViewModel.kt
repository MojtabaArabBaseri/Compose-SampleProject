package ir.millennium.composesample.ui.activities.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.entity.TypeLanguage
import ir.millennium.composesample.core.model.entity.TypeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val statusThemeFlow = userPreferencesRepository.statusTheme
    private val _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

    private val languageAppFlow = userPreferencesRepository.languageApp
    private val _languageApp = runBlocking { MutableStateFlow(languageAppFlow.first()) }
    val languageApp: StateFlow<String> = _languageApp

    private val _authScreen = MutableStateFlow(true)
    val authScreen: StateFlow<Boolean> = _authScreen

    init {
        viewModelScope.launch {
            statusThemeFlow.collect { newTheme ->
                onThemeChanged(newTheme)
            }
            languageAppFlow.collect { newLanguage ->
                onLanguageChanged(newLanguage)
            }
        }
    }

    private fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                _typeTheme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                _typeTheme.value = TypeTheme.DARK.typeTheme
            }
        }
    }

    private fun onLanguageChanged(newLanguage: String) {
        when (newLanguage) {
            TypeLanguage.ENGLISH.typeLanguage -> {
                _languageApp.value = TypeLanguage.ENGLISH.typeLanguage
            }

            TypeLanguage.PERSIAN.typeLanguage -> {
                _languageApp.value = TypeLanguage.PERSIAN.typeLanguage
            }
        }

    }

    fun onAuthScreen(authScreen: Boolean) {
        this._authScreen.value = authScreen
    }
}