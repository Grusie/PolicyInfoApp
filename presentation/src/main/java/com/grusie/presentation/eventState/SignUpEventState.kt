package com.grusie.presentation.eventState


sealed class SignUpEventState {
    data class Alert(val alert: Int) : SignUpEventState()
    data class Error(val error: Exception) : SignUpEventState()
}
