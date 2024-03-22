package com.grusie.domain.usecase.authusecases.local

data class LocalAuthUseCases(
    val createLocalAuthUseCase: CreateLocalAuthUseCase,
    val getLocalAuthUseCase: GetLocalAuthUseCase,
    val deleteUserInfoUseCase: DeleteLocalAuthUseCase
)