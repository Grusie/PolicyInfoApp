package com.grusie.domain.usecase.authusecases.local

import com.grusie.domain.model.LocalAuth
import com.grusie.domain.repository.LocalAuthRepository
import kotlinx.coroutines.flow.Flow

class GetLocalAuthUseCase(private val repository: LocalAuthRepository) {
    suspend operator fun invoke() : Flow<LocalAuth> {
        return repository.getLocalAuth()
    }
}