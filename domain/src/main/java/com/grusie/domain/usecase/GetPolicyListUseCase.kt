package com.grusie.domain.usecase

import com.grusie.domain.repository.PolicyRepository

class GetPolicyListUseCase(private val repository: PolicyRepository) {
    suspend operator fun invoke(
        apiKey: String,
        display: Int,
        page: Int,
    ) = repository.getPolicyList(apiKey, display, page)
}