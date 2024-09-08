package ir.millennium.composesample.feature.splash.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.model.TypeTheme
import ir.millennium.composesample.core.model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SplashScreenViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel(), ISplashScreenViewModel {

    private val stateThemeFlow = userPreferencesRepository.stateTheme
    override val typeTheme = stateThemeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = TypeTheme.LIGHT.typeTheme
    )

    private val _authState = MutableStateFlow<AuthState?>(null)
    override val authState: StateFlow<AuthState?> = _authState.asStateFlow()

    override fun isUserLogin() {
        val currentUser = googleAuthUiClient.getSignedInUser()
        currentUser?.let { userData ->
            _authState.update { AuthState.Authenticated(userData) }
        } ?: run {
            _authState.update { AuthState.Unauthenticated }
        }
    }

    override fun saveUserData(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) { userPreferencesRepository.setDataUser(userData) }
    }
}