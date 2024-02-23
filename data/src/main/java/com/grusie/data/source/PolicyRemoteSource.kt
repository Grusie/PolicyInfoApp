package com.grusie.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.grusie.data.db.PolicyInfoDB
import com.grusie.data.model.PolicyItem
import com.grusie.data.paging.PolicyRemoteMediator
import com.grusie.data.service.PolicyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface PolicyRemoteSource {
    suspend fun getPolicyList(): Flow<PagingData<PolicyItem>>
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
            config = PagingConfig(pageSize = 10),
            remoteMediator = PolicyRemoteMediator(
                policyService,
                policyInfoDB
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .flowOn(Dispatchers.IO)
    }
}