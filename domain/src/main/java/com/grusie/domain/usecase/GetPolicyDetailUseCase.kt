package com.grusie.domain.usecase

import com.grusie.domain.repository.PolicyRepository

class GetPolicyDetailUseCase(private val policyRepository: PolicyRepository) {
    suspend operator fun invoke(
        policyId: String
    ) = policyRepository.getPolicyInfo(policyId)
}