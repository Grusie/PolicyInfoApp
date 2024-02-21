package com.grusie.presentation.uiState

import com.grusie.domain.model.PolicyDetail

sealed class PolicyDetailUiState {
    object Empty : PolicyDetailUiState()
    object Loading : PolicyDetailUiState()
    data class Success(val policyDetail: PolicyDetail) : PolicyDetailUiState()

    data class Error(val error: Exception) : PolicyDetailUiState()
}