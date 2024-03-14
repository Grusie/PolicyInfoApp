package com.grusie.domain.usecase.PolicyUseCases

import com.grusie.domain.repository.PolicyRepository

class GetPolicyListUseCase(private val repository: PolicyRepository) {
    suspend operator fun invoke() = repository.getPolicyList()
}