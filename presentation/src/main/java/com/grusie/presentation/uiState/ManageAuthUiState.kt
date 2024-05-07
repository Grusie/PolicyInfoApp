package com.grusie.presentation.uiState

sealed class ManageAuthUiState {
    object Init : ManageAuthUiState()
    object Loading : ManageAuthUiState()
    object SuccessSignOut : ManageAuthUiState()
}
