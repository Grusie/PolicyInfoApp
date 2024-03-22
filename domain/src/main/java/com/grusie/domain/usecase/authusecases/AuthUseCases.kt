package com.grusie.domain.usecase.authusecases

import com.grusie.domain.usecase.authusecases.email.EmailAuthUseCases
import com.grusie.domain.usecase.authusecases.local.LocalAuthUseCases

data class AuthUseCases(
    val emailAuthUseCases: EmailAuthUseCases,
    val localAuthUseCases: LocalAuthUseCases
)
