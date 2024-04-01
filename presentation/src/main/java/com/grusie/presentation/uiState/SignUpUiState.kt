package com.grusie.presentation.uiState

sealed class SignUpUiState{
    object Init : SignUpUiState()
    object Loading : SignUpUiState()
    object SuccessSendEmail : SignUpUiState()
    object SuccessSignUp : SignUpUiState()
}