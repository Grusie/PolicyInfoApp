package com.grusie.domain.usecase.policyusecases

data class PolicyUseCases(
    val getPolicyListUseCase: GetPolicyListUseCase,
    val getSearchPolicyListUseCase: GetSearchPolicyListUseCase,
    val getPolicyDetailUseCase: GetPolicyDetailUseCase
)
