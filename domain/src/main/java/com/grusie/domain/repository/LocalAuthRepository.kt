package com.grusie.domain.repository

import com.grusie.domain.model.LocalAuth
import kotlinx.coroutines.flow.Flow

interface LocalAuthRepository {
    suspend fun getLocalAuth(): Flow<LocalAuth>
    suspend fun deleteLocalAuth()
    suspend fun createLocalAuth(localAuth: LocalAuth)
}