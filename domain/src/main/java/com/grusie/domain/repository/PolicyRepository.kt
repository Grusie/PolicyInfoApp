package com.grusie.domain.repository

import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow

interface PolicyRepository {
    suspend fun getPolicyList(apiKey: String, display:Int, page: Int) : List<PolicySimple>
    suspend fun getPolicyInfo(apiKey: String, policyId:String) : PolicyDetail
}