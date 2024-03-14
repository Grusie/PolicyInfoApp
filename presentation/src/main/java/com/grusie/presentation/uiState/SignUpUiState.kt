package com.grusie.presentation.uiState

sealed class SignUpUiState{
    object Empty : SignUpUiState()
    object Loading : SignUpUiState()
    object SuccessSendEmail : SignUpUiState()
    object SuccessSignUp : SignUpUiState()
    data class Alert(val alert: Int) : SignUpUiState()
    data class Error(val error: Exception) : SignUpUiState()
}