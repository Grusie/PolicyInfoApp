package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.usecase.PolicyUseCases
import com.grusie.presentation.uiState.PolicyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyListViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    BaseViewModel() {
    private val _policyListUiState: MutableStateFlow<PolicyListUiState> =
        MutableStateFlow(PolicyListUiState.Loading)
    val policyListUiState: StateFlow<PolicyListUiState> = _policyListUiState

    private val _policyList: MutableStateFlow<PagingData<PolicySimple>> =
        MutableStateFlow(PagingData.empty())
    val policyList: Flow<PagingData<PolicySimple>> = _policyList

    init {
        getPolicyList()
    }

    private fun getPolicyList() {
        viewModelScope.launch {
            setPolicyUiState(PolicyListUiState.Loading)
            try {
                policyUseCases.getPolicyListUseCase().cachedIn(viewModelScope)
                    .collect { pagingData ->
                        _policyList.emit(pagingData)
                    }
            } catch (e: Exception) {
                setPolicyUiState(PolicyListUiState.Error(e))
            }
        }
    }

    fun setPolicyUiState(uiState: PolicyListUiState) {
        viewModelScope.launch {
            _policyListUiState.emit(uiState)
        }
    }

    override fun loading(): Job {
        getPolicyList()
        return super.loading()
    }
}

