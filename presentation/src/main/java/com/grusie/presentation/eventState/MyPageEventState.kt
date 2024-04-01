package com.grusie.presentation.eventState

sealed class MyPageEventState {
    object Init : MyPageEventState()
    object Loading : MyPageEventState()
    data class Alert(val alert: Int) : MyPageEventState()
    data class Error(val error: Exception) : MyPageEventState()
}