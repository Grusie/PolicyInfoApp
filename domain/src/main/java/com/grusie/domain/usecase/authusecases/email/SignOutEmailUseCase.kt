package com.grusie.domain.usecase.authusecases.email

import com.google.firebase.auth.FirebaseAuth

class SignOutEmailUseCase(
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke() {

    }
}