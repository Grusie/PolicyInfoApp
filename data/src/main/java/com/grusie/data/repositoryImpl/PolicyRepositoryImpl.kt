package com.grusie.data.repositoryImpl

import com.grusie.data.BuildConfig
import com.grusie.data.model.toPolicyDetail
import com.grusie.data.model.toPolicySimple
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.repository.PolicyRepository
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(private val policyRemoteSource: PolicyRemoteSource) :
    PolicyRepository {
    override suspend fun getPolicyList(
        apiKey: String,
        display: Int,
        page: Int
    ): List<PolicySimple> {
        return policyRemoteSource.getPolicyList(
            apiKey = BuildConfig.POLICY_API_KEY,
            display,
            page
        ).youthPolicy.map { it.toPolicySimple() }
    }

    override suspend fun getPolicyInfo(
        apiKey: String,
        policyId: String,
    ): PolicyDetail {
        return policyRemoteSource.getPolicyDetail(
            apiKey = apiKey,
            policyId = policyId,
        ).toPolicyDetail()
    }
}