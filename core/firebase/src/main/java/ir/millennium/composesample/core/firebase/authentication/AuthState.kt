package ir.millennium.composesample.core.firebase.authentication

import ir.millennium.composesample.core.model.UserData

sealed class AuthState {
    data object Initialization : AuthState()
    data class Authenticated(val user: UserData?) : AuthState()
    data class Error(val exception: Exception?) : AuthState()
    data object Unauthenticated : AuthState()
}