package com.grusie.domain.usecase.userusecases

import com.grusie.domain.repository.UserInfoRepository

data class UserUseCases(
    val createUserInfoUseCase: CreateUserInfoUseCase,
    val updateUserInfoUseCase: UpdateUserInfoUseCase,
    val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
)