package com.grusie.data.repositoryImpl

import com.grusie.data.model.toLocalAuth
import com.grusie.data.source.LocalAuthSource
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.repository.LocalAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalAuthRepositoryImpl @Inject constructor(
    private val localAuthSource: LocalAuthSource,
) : LocalAuthRepository {
    override suspend fun getLocalAuth(): Flow<LocalAuth> {
        return localAuthSource.getLocalAuth().map {
            it.toLocalAuth()
        }
    }

    override suspend fun deleteLocalAuth() {
        localAuthSource.deleteLocalAuth()
    }

    override suspend fun createLocalAuth(localAuth: LocalAuth) {
        localAuthSource.createLocalAuth(localAuth)
    }
}