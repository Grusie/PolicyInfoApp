package com.grusie.presentation.uiState

sealed class ManageAuthUiState {
    object Empty : ManageAuthUiState()
    object Loading : ManageAuthUiState()
    object SuccessSignOut : ManageAuthUiState()
    data class Alert(val alert: Int) : ManageAuthUiState()
    data class Error(val error: Exception) : ManageAuthUiState()
}
