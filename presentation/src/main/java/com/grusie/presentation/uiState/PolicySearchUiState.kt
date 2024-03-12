package com.grusie.presentation.uiState

sealed class PolicySearchUiState {
    object Empty : PolicySearchUiState()
    object Loading : PolicySearchUiState()
    object Success : PolicySearchUiState()
    object Search : PolicySearchUiState()

    data class Error(val error: Exception) : PolicySearchUiState()
}