package com.grusie.domain.usecase.authusecases.local

import com.grusie.domain.model.LocalAuth
import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.UserInfoRepository

class CreateLocalAuthUseCase(private val repository: LocalAuthRepository) {
    suspend operator fun invoke(localAuth: LocalAuth){
        repository.deleteLocalAuth()
        repository.createLocalAuth(localAuth = localAuth)
    }
}