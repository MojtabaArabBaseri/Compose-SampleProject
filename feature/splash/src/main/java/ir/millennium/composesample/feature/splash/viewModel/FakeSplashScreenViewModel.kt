package ir.millennium.composesample.feature.splash.viewModel

import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.model.UserData
import kotlinx.coroutines.flow.StateFlow

class FakeSplashScreenViewModel(
    override val typeTheme: StateFlow<Int>,
    override val authState: StateFlow<AuthState?>
) : ISplashScreenViewModel {
    override fun isUserLogin() {
        throw NotImplementedError()
    }

    override fun saveUserData(userData: UserData) {
        throw NotImplementedError()
    }
}