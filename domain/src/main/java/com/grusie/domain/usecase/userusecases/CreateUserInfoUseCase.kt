package com.grusie.domain.usecase.userusecases

import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.UserInfoRepository

class CreateUserInfoUseCase(private val repository: UserInfoRepository) {
    suspend operator fun invoke(userInfo: UserInfo){
        repository.createUserInfo(userInfo = userInfo)
    }
}