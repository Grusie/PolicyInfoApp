package com.grusie.data.service

import com.grusie.data.BuildConfig
import com.grusie.data.model.PolicyList
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface PolicyService {
    @GET("youthPlcyList.do")
    suspend fun getPolicyList(
        @Query("openApiVlak")
        apiKey: String = BuildConfig.POLICY_API_KEY,
        @Query("display") display: Int = 10,
        @Query("pageIndex") page: Int = 1,
        @Query("query") query: String? = null,
        @Query("bizTycdSel") policyTypeCode: Int? = null,
        @Query("srchPolyBizSecd") policyRegionCode: Int? = null,
        @Query("keyword") keyword: String? = null,
        @Query("srchPolicyId") policyId: String? = null
    ): PolicyList
}