package ir.millennium.composesample.core.firebase.authentication

import ir.millennium.composesample.core.model.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: Exception?
)