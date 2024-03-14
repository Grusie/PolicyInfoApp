package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.usecase.PolicyUseCases.PolicyUseCases
import com.grusie.presentation.uiState.PolicySearchUiState
import com.grusie.presentation.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicySearchViewModel @Inject constructor(private val policyUseCases: PolicyUseCases) :
    BaseViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _policySearchUiState: MutableStateFlow<PolicySearchUiState> =
        MutableStateFlow(PolicySearchUiState.Search)
    val policySearchUiState: StateFlow<PolicySearchUiState> = _policySearchUiState

    private val _searchPolicyList: MutableStateFlow<PagingData<PolicySimple>> =
        MutableStateFlow(PagingData.empty())
    val searchPolicyList: Flow<PagingData<PolicySimple>> = _searchPolicyList

    @OptIn(FlowPreview::class)
    val debouncedSearchQuery: Flow<String?> = searchQuery
        .debounce(Constant.SEARCH_TIME_DELAY)
        .filter { it.trim().isNotEmpty() }
        // Flow 가 되었기 때문에 distinctUntilChanged 를 달아줘 이전의 값에 대해 필터링
        .distinctUntilChanged()

    var currentQuery: String? = null

    init {
        viewModelScope.launch {
            debouncedSearchQuery.collect { query ->
                if (!query.isNullOrEmpty()) {
                    currentQuery = query
                    doSearch()
                }
            }
        }
    }

    fun doSearch(){
        if(!currentQuery.isNullOrEmpty()){
            getPolicyList(currentQuery!!)
        }
    }
    private fun getPolicyList(query: String) {
        viewModelScope.launch {
            try {
                setPolicyUiState(PolicySearchUiState.Loading)
                Log.d("confirm getPolicyList : ", query)
                policyUseCases.getSearchPolicyListUseCase(query = query)
                    .cachedIn(viewModelScope)
                    .collect { pagingData ->
                        _searchPolicyList.emit(pagingData)
                    }
            } catch (e: Exception) {
                setPolicyUiState(PolicySearchUiState.Error(e))
            }
        }
    }

    fun changeSearchQuery(query: String) {
        Log.d("confirm changeSearchQuery :", query)
        _searchQuery.value = query
    }

    fun setPolicyUiState(uiState: PolicySearchUiState) {
        viewModelScope.launch {
            _policySearchUiState.emit(uiState)
            Log.d("confirm PolicySearchUiState viewModel : ", "$uiState, ${_policySearchUiState.value}")
        }
    }

    override fun loading(): Job {
        doSearch()
        return super.loading()
    }
}