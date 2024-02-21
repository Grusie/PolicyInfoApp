package com.grusie.domain.usecase

import com.grusie.domain.repository.PolicyRepository

class GetPolicyDetailUseCase(private val policyRepository: PolicyRepository) {
    suspend operator fun invoke(
        apiKey: String,
        policyId: String
    ) = policyRepository.getPolicyInfo(apiKey, policyId)
}