package com.grusie.presentation.uiState

sealed class SignInUiState {
    object Empty : SignInUiState()
    object Loading : SignInUiState()
    object SuccessSignIn : SignInUiState()
    data class Alert(val alert: Int) : SignInUiState()
    data class Error(val error: Exception) : SignInUiState()
}