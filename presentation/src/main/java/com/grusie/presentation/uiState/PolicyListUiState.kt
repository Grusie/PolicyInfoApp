package com.grusie.presentation.uiState

sealed class PolicyListUiState {
    object Empty : PolicyListUiState()
    object Loading : PolicyListUiState()
    object Success : PolicyListUiState()
    data class Error(val error: Exception) : PolicyListUiState()
}
