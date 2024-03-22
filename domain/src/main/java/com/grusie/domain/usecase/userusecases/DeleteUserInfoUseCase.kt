package com.grusie.domain.usecase.userusecases

import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.UserInfoRepository

class DeleteUserInfoUseCase(private val repository: UserInfoRepository) {
    suspend operator fun invoke(userInfo: UserInfo){
        repository.deleteUserInfo(userInfo = userInfo)
    }
}