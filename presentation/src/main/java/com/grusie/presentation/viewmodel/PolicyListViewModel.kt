package com.grusie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.data.BuildConfig
import com.grusie.domain.usecase.PolicyUseCases
import com.grusie.presentation.uiState.PolicyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyListViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    ViewModel() {
    private val _policyListUiState: MutableStateFlow<PolicyListUiState> =
        MutableStateFlow(PolicyListUiState.Loading)
    val policyListUiState: StateFlow<PolicyListUiState> = _policyListUiState

    init {
        getPolicyList()
    }

    fun getPolicyList(display: Int = 10, page: Int = 1) {
        viewModelScope.launch {
            try {
                val policySimpleList =
                    policyUseCases.getPolicyListUseCase(BuildConfig.POLICY_API_KEY, display, page)
                _policyListUiState.emit(
                    if (policySimpleList.isNotEmpty()) PolicyListUiState.Success(
                        policySimpleList
                    ) else PolicyListUiState.Empty
                )
            } catch (e: Exception) {
                _policyListUiState.emit(PolicyListUiState.Error(e))
            }
        }
    }
}

