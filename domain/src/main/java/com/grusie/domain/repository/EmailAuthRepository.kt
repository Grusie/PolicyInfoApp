package com.grusie.domain.repository

import androidx.paging.PagingData
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow

interface EmailAuthRepository {
    suspend fun emailSignUp(): Flow<PagingData<PolicySimple>>

    suspend fun emailSignIn(
        query: String? = null,
        policyTypeCode: Int? = null,
        policyRegionCode: Int? = null,
        keyword: String? = null
    ): Flow<PagingData<PolicySimple>>

    suspend fun emailSignOut(policyId: String): Flow<PolicyDetail>

    suspend fun emailSend(email: String)
}