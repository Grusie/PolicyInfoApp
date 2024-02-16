package com.grusie.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.data.BuildConfig
import com.grusie.domain.usecase.PolicyUseCases
import com.grusie.presentation.ui.uiState.PolicyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    ViewModel() {
    private val _policyListUiState: MutableStateFlow<PolicyListUiState> =
        MutableStateFlow(PolicyListUiState.Loading)
    val policyListUiState: StateFlow<PolicyListUiState> = _policyListUiState

    fun getPolicyList(display: Int = 10, page: Int = 1) {
        viewModelScope.launch {
            try {
                val policySimpleList = policyUseCases.getPolicyListUseCase(BuildConfig.POLICY_API_KEY, display, page)
                _policyListUiState.emit(if (policySimpleList.isNotEmpty()) PolicyListUiState.Success(policySimpleList) else PolicyListUiState.Empty)
                Log.d("confirm policySimpleList : ", "${_policyListUiState.value} $policySimpleList")

            } catch (e: Exception) {
                _policyListUiState.emit(PolicyListUiState.Error(e.message ?: ""))
            }
        }
    }
}

