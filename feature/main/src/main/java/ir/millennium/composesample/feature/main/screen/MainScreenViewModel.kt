package ir.millennium.composesample.feature.main.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainScreenViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    val stateUserData = userPreferencesRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) { googleAuthUiClient.signOut() }
    }
}