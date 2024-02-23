package com.grusie.domain.repository

import androidx.paging.PagingData
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow

interface PolicyRepository {
    suspend fun getPolicyList() : Flow<PagingData<PolicySimple>>
    suspend fun getPolicyInfo(policyId:String) : Flow<PolicyDetail>
}