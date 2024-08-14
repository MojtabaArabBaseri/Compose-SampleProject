package ir.millennium.composesample.feature.splash.viewModel

import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface ISplashScreenViewModel {

    val typeTheme: StateFlow<Int>

    val authState: StateFlow<AuthState?>
    fun isUserLogin()
    fun saveUserData(userData: UserData)
}