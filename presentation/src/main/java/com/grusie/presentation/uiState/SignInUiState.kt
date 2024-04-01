package com.grusie.presentation.uiState

sealed class SignInUiState {
    object Init : SignInUiState()
    object Loading : SignInUiState()
    object SuccessSignIn : SignInUiState()
}