package com.example.expensetrackerapp.login

data class SignInState(
    val isSignInSuceesful: Boolean = false,
    val signInError : String? = null
)
