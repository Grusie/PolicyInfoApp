package com.grusie.presentation.ui.uiState

import com.grusie.domain.model.PolicySimple

sealed class PolicyDetailUiState {
    object Empty:PolicyDetailUiState()
    object Loading: PolicyDetailUiState()
    data class Success(val policyList: List<PolicySimple>): PolicyDetailUiState()

    data class Error(val errorMsg: String): PolicyDetailUiState()
}