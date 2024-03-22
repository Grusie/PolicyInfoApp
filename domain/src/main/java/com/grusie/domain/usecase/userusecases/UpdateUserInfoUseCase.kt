package com.grusie.domain.usecase.userusecases

import com.grusie.domain.repository.UserInfoRepository

class UpdateUserInfoUseCase(private val repository: UserInfoRepository) {
    suspend operator fun invoke(id: String){
        repository.getUserInfo(id = id)
    }
}