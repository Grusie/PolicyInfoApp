package com.grusie.domain.usecase.policyusecases

import com.grusie.domain.repository.PolicyRepository

class GetPolicyListUseCase(private val repository: PolicyRepository) {
    suspend operator fun invoke() = repository.getPolicyList()
}