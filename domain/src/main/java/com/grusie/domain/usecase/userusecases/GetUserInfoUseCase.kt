package com.grusie.domain.usecase.userusecases

import com.grusie.domain.repository.UserInfoRepository

class GetUserInfoUseCase(private val repository: UserInfoRepository) {
    suspend operator fun invoke(id: String){
        repository.getUserInfo(id = id)
    }
}