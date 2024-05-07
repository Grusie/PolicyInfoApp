package com.grusie.domain.repository

import androidx.paging.PagingData
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow

interface PolicyRepository {
    suspend fun getPolicyList(): Flow<PagingData<PolicySimple>>

    suspend fun getSearchPolicyList(
        query: String? = null,
        policyTypeCode: Int? = null,
        policyRegionCode: Int? = null,
        keyword: String? = null
    ): Flow<PagingData<PolicySimple>>

    suspend fun getPolicyInfo(policyId: String): Flow<PolicyDetail>

    suspend fun getFavoritePolicyList(idList: List<String>) : Flow<PagingData<PolicySimple>>
}