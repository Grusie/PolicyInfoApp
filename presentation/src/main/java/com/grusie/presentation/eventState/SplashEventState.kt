package com.grusie.presentation.eventState

sealed class SplashEventState {
    data class Alert(val alert: Int) : SplashEventState()
    data class Error(val error: Exception) : SplashEventState()
    data class Toast(val code: Int) : SplashEventState()
}