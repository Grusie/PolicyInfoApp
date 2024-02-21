package com.grusie.presentation.uiState

import com.grusie.domain.model.PolicySimple

sealed class PolicyListUiState {
    object Empty : PolicyListUiState()
    object Loading : PolicyListUiState()
    data class Success(val policySimpleList: List<PolicySimple>) : PolicyListUiState()

    data class Error(val error: Exception) : PolicyListUiState()
}
