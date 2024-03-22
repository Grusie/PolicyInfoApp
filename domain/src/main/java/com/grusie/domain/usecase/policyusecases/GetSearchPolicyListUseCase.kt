package com.grusie.domain.usecase.policyusecases

import com.grusie.domain.repository.PolicyRepository

class GetSearchPolicyListUseCase(private val repository: PolicyRepository) {
    suspend operator fun invoke(
        query: String? = null,
        policyTypeCode: Int? = null,
        policyRegionCode: Int? = null,
        keyword: String? = null
    ) = repository.getSearchPolicyList(
        query = query,
        policyTypeCode = policyTypeCode,
        policyRegionCode = policyRegionCode,
        keyword = keyword
    )
}
