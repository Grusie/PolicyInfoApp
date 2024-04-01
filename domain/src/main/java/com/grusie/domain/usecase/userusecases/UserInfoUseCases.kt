package com.grusie.domain.usecase.userusecases

data class UserInfoUseCases(
    val createUserInfoUseCase: CreateUserInfoUseCase,
    val updateUserInfoUseCase: UpdateUserInfoUseCase,
    val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
)