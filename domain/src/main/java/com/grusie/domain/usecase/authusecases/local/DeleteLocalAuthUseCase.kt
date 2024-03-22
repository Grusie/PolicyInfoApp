package com.grusie.domain.usecase.authusecases.local

import com.grusie.domain.repository.LocalAuthRepository

class DeleteLocalAuthUseCase(private val repository: LocalAuthRepository) {
    suspend operator fun invoke() {
        repository.deleteLocalAuth()
    }
}