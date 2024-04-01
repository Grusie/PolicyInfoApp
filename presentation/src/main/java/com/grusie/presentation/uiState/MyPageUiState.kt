package com.grusie.presentation.uiState

import com.grusie.domain.model.UserInfo


sealed class MyPageUiState {
    object Init : MyPageUiState()
    object Loading : MyPageUiState()
    data class SuccessLogIn(val data: UserInfo?) : MyPageUiState()
}