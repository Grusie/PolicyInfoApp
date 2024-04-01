package com.grusie.presentation.eventState

sealed class SignInEventState {
    object Init : SignUpEventState()

    data class Alert(val alert: Int) : SignInEventState()
    data class Error(val error: Exception) : SignInEventState()
}