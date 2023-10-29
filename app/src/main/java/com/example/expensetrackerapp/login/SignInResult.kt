package com.example.expensetrackerapp.login



data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)
data class UserData(
    val userId : String,
    val userName: String?,
    val profilePicURL: String?
)