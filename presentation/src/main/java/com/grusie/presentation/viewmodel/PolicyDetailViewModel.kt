package com.grusie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.data.BuildConfig
import com.grusie.domain.usecase.PolicyUseCases
import com.grusie.presentation.uiState.PolicyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyDetailViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    ViewModel() {
    private val _policyDetailUiState: MutableStateFlow<PolicyDetailUiState> = MutableStateFlow(
        PolicyDetailUiState.Loading
    )
    val policyDetailUiState: StateFlow<PolicyDetailUiState> = _policyDetailUiState

    fun getPolicyDetail(policyId: String) {
        viewModelScope.launch {
            try {
                val policyDetail =
                    policyUseCases.getPolicyDetailUseCase(BuildConfig.POLICY_API_KEY, policyId)
                _policyDetailUiState.emit(
                    PolicyDetailUiState.Success(policyDetail)
                )
            } catch (e: Exception) {
                _policyDetailUiState.emit(PolicyDetailUiState.Error(e))
            }
        }
    }

    fun setScrap(policyId: String) {
        //TODO : 파이어베이스를 통해, 스크랩을 구현할 예정
    }
}