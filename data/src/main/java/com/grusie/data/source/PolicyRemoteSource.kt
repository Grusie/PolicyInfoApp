package com.grusie.data.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.grusie.data.db.PolicyInfoDB
import com.grusie.data.model.PolicyItem
import com.grusie.data.paging.PolicyPagingSource
import com.grusie.data.paging.PolicyRemoteMediator
import com.grusie.data.service.PolicyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.RuntimeException
import javax.inject.Inject

interface PolicyRemoteSource {
    suspend fun getPolicyList(): Flow<PagingData<PolicyItem>>
    suspend fun getSearchPolicyList(
        query: String?,
        policyTypeCode: Int?,
        policyRegionCode: Int?,
        keyword: String?
    ): Flow<PagingData<PolicyItem>>

    suspend fun getPolicyInfo(policyId: String?) : Flow<PolicyItem>
}

class PolicyRemoteSourceImpl @Inject constructor(
    private val policyService: PolicyService,
    private val policyInfoDB: PolicyInfoDB
) : PolicyRemoteSource {
    private val policyInfoDao = policyInfoDB.policyDao()

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPolicyList(): Flow<PagingData<PolicyItem>> {
        val pagingSourceFactory = { policyInfoDao.getAllPolicies() }
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10),
            remoteMediator = PolicyRemoteMediator(
                policyService = policyService,
                policyInfoDB = policyInfoDB
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getSearchPolicyList(
        query: String?,
        policyTypeCode: Int?,
        policyRegionCode: Int?,
        keyword: String?
    ): Flow<PagingData<PolicyItem>> {
        val pagingSourceFactory = {
            PolicyPagingSource(
                policyService = policyService,
                query = query,
                policyTypeCode = policyTypeCode,
                policyRegionCode = policyRegionCode,
                keyword = keyword
            )
        }
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getPolicyInfo(policyId: String?): Flow<PolicyItem> = flow {
        val policyItem = policyService.getPolicyList(policyId = policyId).youthPolicy?.get(0)!!
        emit(policyItem)
    }
}