package com.grusie.domain.usecase.authusecases.email
data class EmailAuthUseCases(
    val signUpEmailUseCase : SignUpEmailUseCase,
    val signInEmailUseCase : SignInEmailUseCase,
    val signOutEmailUseCase : SignOutEmailUseCase,
    val sendEmailUseCase : SendEmailUseCase,
    val verifyEmailUseCase : VerifyEmailUseCase,
    val deleteEmailUseCase: DeleteEmailUseCase
)