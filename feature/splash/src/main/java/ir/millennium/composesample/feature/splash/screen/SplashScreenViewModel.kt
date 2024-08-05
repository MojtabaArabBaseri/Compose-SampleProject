package ir.millennium.composesample.feature.splash.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class SplashScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val statusThemeFlow = userPreferencesRepository.statusTheme
    private var _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState: StateFlow<AuthState?> = _authState.asStateFlow()

    fun isUserLogin() {
        val currentUser = googleAuthUiClient.getSignedInUser()
        if (currentUser != null) {
            _authState.update { AuthState.Authenticated(currentUser) }
        } else {
            _authState.update { AuthState.Unauthenticated }
        }
    }

    fun saveUserData(userData: UserData) {
        viewModelScope.launch { userPreferencesRepository.setDataUser(userData) }
    }
}