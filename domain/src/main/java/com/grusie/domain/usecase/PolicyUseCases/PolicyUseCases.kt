package com.grusie.domain.usecase.PolicyUseCases

data class PolicyUseCases(
    val getPolicyListUseCase: GetPolicyListUseCase,
    val getSearchPolicyListUseCase: GetSearchPolicyListUseCase,
    val getPolicyDetailUseCase: GetPolicyDetailUseCase
)
