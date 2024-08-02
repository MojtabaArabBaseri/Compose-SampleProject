package ir.millennium.composesample.feature.login.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.firebase.authentication.SignInResult
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class LoginScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val statusThemeFlow = userPreferencesRepository.statusTheme
    private var _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initialization)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun onSignInResult(result: SignInResult) {

        if (result.data != null) {
            _authState.value = AuthState.Authenticated(result.data)
        } else {
            _authState.value = AuthState.Error(result.errorMessage)
        }
    }

    fun saveDataUser(userData: UserData) {
        viewModelScope.launch { userPreferencesRepository.setDataUser(userData) }
    }

    fun resetState() {
        _authState.value = AuthState.Initialization
    }
}