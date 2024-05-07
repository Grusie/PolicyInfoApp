package com.grusie.presentation.eventState
sealed class ManageAuthEventState {
    object SuccessChangeNickname : ManageAuthEventState()
    data class Alert(val alert: Int) : ManageAuthEventState()
    data class Error(val error: Exception) : ManageAuthEventState()
}