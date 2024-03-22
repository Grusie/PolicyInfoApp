package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.policyusecases.PolicyUseCases
import com.grusie.presentation.uiState.PolicyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyDetailViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    BaseViewModel() {
    private val _policyDetailUiState: MutableStateFlow<PolicyDetailUiState> = MutableStateFlow(
        PolicyDetailUiState.Loading
    )
    val policyDetailUiState: StateFlow<PolicyDetailUiState> = _policyDetailUiState

    fun getPolicyDetail(policyId: String) {
        viewModelScope.launch {
            _policyDetailUiState.emit(PolicyDetailUiState.Loading)
            try {
                policyUseCases.getPolicyDetailUseCase(policyId).collect {
                    _policyDetailUiState.emit(PolicyDetailUiState.Success(it))
                }

            } catch (e: Exception) {
                _policyDetailUiState.emit(PolicyDetailUiState.Error(e))
            }
        }
    }

    fun setScrap(policyId: String) {
        //TODO : 파이어베이스를 통해, 스크랩을 구현할 예정
    }
}