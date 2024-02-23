package com.grusie.data.service

import com.grusie.data.model.PolicyList
import retrofit2.http.GET
import retrofit2.http.Query

interface PolicyService {
    @GET("youthPlcyList.do")
    suspend fun getPolicyList(
        @Query("openApiVlak")
        apiKey: String,
        @Query("display") display: Int = 10,
        @Query("pageIndex") page: Int = 1
    ): PolicyList
}