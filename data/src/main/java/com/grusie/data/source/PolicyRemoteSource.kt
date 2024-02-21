package com.grusie.data.source

import com.grusie.data.model.PolicyList
import com.grusie.data.service.PolicyService
import javax.inject.Inject

interface PolicyRemoteSource {
    suspend fun getPolicyList(
        apiKey: String,
        display: Int = 10,
        page: Int = 1
    ): PolicyList

    suspend fun getPolicyDetail(
        apiKey: String,
        policyId: String,
        display: Int = 10,
        page: Int = 1
    ): PolicyList
}

class PolicyRemoteSourceImpl @Inject constructor(
    private val policyService: PolicyService
) : PolicyRemoteSource {
    override suspend fun getPolicyList(
        apiKey: String,
        display: Int,
        page: Int
    ): PolicyList {
        return policyService.getPolicyList(apiKey = apiKey, display = display, page = page)
    }

    override suspend fun getPolicyDetail(
        apiKey: String,
        policyId: String,
        display: Int,
        page: Int
    ): PolicyList {
        return policyService.getPolicyDetail(
            apiKey = apiKey,
            policyId = policyId,
            display = display,
            page = page
        )
    }
}