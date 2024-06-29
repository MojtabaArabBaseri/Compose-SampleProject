package ir.millennium.composesample.feature.splash.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class SplashScreenViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val statusThemeFlow = userPreferencesRepository.statusTheme
    private var _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

}