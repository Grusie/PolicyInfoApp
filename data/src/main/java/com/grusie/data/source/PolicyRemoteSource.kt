package com.grusie.data.source

import android.util.Log
import com.grusie.data.model.PolicyItem
import com.grusie.data.model.PolicyList
import com.grusie.data.service.PolicyService
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow
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
    ): PolicyItem
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
    ): PolicyItem {
        return policyService.getPolicyDetail(
            apiKey = apiKey,
            policyId = policyId,
            display = display,
            page = page
        )
    }
}