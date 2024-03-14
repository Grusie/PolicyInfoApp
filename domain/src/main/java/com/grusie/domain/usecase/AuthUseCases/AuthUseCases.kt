package com.grusie.domain.usecase.AuthUseCases
data class AuthUseCases(
    val emailSignUpUseCase : EmailSignUpUseCase,
    val emailLoginUseCase : EmailLoginUseCase,
    val emailLogoutUseCase : EmailLogoutUseCase,
    val emailSendUseCase : EmailSendUseCase
)