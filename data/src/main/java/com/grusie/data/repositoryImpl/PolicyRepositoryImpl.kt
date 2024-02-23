package com.grusie.data.repositoryImpl

import androidx.paging.PagingData
import androidx.paging.map
import com.grusie.data.model.toPolicyDetail
import com.grusie.data.model.toPolicySimple
import com.grusie.data.source.PolicyLocalDataSource
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.repository.PolicyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
    private val policyRemoteSource: PolicyRemoteSource,
    private val policyLocalDataSource: PolicyLocalDataSource
) :
    PolicyRepository {
    override suspend fun getPolicyList(): Flow<PagingData<PolicySimple>> =
        policyRemoteSource.getPolicyList().map { pagingData ->
            pagingData.map {
                it.toPolicySimple()
            }
        }

    override suspend fun getPolicyInfo(
        policyId: String
    ): Flow<PolicyDetail> {
        return policyLocalDataSource.getPoliciesFromDB(
            policyId = policyId,
        ).map { it.toPolicyDetail() }
    }
}