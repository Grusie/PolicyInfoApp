package com.grusie.domain.usecase

import com.grusie.domain.repository.PolicyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetPolicyDetailUseCase(private val policyRepository: PolicyRepository) {
    operator fun invoke(
        apiKey: String,
        scope: CoroutineScope,
        policyId: String
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                policyRepository.getPolicyInfo(apiKey, policyId)
            }
        }
    }
}