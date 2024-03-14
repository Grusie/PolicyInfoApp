package com.grusie.domain.usecase.AuthUseCases

import com.grusie.domain.repository.EmailAuthRepository

class EmailLogoutUseCase(private val repository: EmailAuthRepository) {
    suspend operator fun invoke(){

    }
}