package com.grusie.domain.usecase.authusecases.local

import com.grusie.domain.model.LocalAuth
import com.grusie.domain.repository.LocalAuthRepository

class UpdateLocalAuthUseCase(private val repository: LocalAuthRepository) {
    suspend operator fun invoke(localAuth: LocalAuth){
        repository.updateLocalAuth(localAuth = localAuth)
    }
}