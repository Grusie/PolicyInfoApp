package com.grusie.data.repositoryImpl

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.grusie.data.source.PolicyLocalDataSource
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.repository.EmailAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmailAuthRepositoryImpl @Inject constructor(
    private val policyRemoteSource: PolicyRemoteSource,
    private val policyLocalDataSource: PolicyLocalDataSource,
    private val firebaseAuth: FirebaseAuth
) : EmailAuthRepository {
    override suspend fun emailSignUp(): Flow<PagingData<PolicySimple>> {
        TODO("Not yet implemented")
    }

    override suspend fun emailLogin(
        query: String?,
        policyTypeCode: Int?,
        policyRegionCode: Int?,
        keyword: String?
    ): Flow<PagingData<PolicySimple>> {
        TODO("Not yet implemented")
    }

    override suspend fun emailLogOut(policyId: String): Flow<PolicyDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun emailSend(email: String) {
    }

}