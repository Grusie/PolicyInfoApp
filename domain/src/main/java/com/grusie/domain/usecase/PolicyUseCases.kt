package com.grusie.domain.usecase

data class PolicyUseCases(
    val getPolicyListUseCase: GetPolicyListUseCase,
    val getSearchPolicyListUseCase: GetSearchPolicyListUseCase,
    val getPolicyDetailUseCase: GetPolicyDetailUseCase
)
