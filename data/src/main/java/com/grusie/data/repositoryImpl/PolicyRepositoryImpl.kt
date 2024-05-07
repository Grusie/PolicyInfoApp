package com.grusie.data.repositoryImpl

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.grusie.data.model.toPolicyDetail
import com.grusie.data.model.toPolicySimple
import com.grusie.data.source.PolicyLocalDataSource
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.repository.PolicyRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
    private val policyRemoteSource: PolicyRemoteSource,
    private val policyLocalDataSource: PolicyLocalDataSource
) :
    PolicyRepository {
    override suspend fun getPolicyList(): Flow<PagingData<PolicySimple>> =
        policyRemoteSource.getPolicyList().map { pagingData ->
            Log.d("confirm pagingData : ", "$pagingData")
            pagingData.map {
                it.toPolicySimple()
            }
        }

    override suspend fun getSearchPolicyList(
        query: String?,
        policyTypeCode: Int?,
        policyRegionCode: Int?,
        keyword: String?
    ): Flow<PagingData<PolicySimple>> =
        policyRemoteSource.getSearchPolicyList(
            query = query,
            policyTypeCode = policyTypeCode,
            policyRegionCode = policyRegionCode,
            keyword = keyword
        ).map { pagingData ->
            pagingData.map {
                it.toPolicySimple()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getPolicyInfo(
        policyId: String
    ): Flow<PolicyDetail> {
        return policyLocalDataSource.getPoliciesFromDB(policyId = policyId)
            .flatMapConcat { localData ->
                if (localData != null) {
                    flow { emit(localData.toPolicyDetail()) }
                } else {
                    policyRemoteSource.getPolicyInfo(policyId = policyId)
                        .map { remoteData -> remoteData.toPolicyDetail() }
                }
            }
    }

    override suspend fun getFavoritePolicyList(idList: List<String>): Flow<PagingData<PolicySimple>> {
        return policyRemoteSource.getFavoritePolicyList(idList).map { pagingData ->
            pagingData.map {
                it.toPolicySimple()
            }
        }
    }
}
