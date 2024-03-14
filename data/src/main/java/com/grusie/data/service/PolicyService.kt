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
        apiKey: String = BuildConfig.POLICY_API_KEY,                //api키
        @Query("display") display: Int = 10,                        //보여줄 페이지 수
        @Query("pageIndex") page: Int = 1,                          //조회할 페이지
        @Query("query") query: String? = null,                      //정책명, 정책소개 정보검색
        @Query("bizTycdSel") policyTypeCode: Int? = null,           //정책 유형
        @Query("srchPolyBizSecd") policyRegionCode: Int? = null,    //지역코드
        @Query("keyword") keyword: String? = null,                  //검색 키워드 예) 채용,취직,구직
        @Query("srchPolicyId") policyId: String? = null             //정책 Id
    ): PolicyList
}