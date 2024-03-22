package com.grusie.domain.usecase.authusecases.email

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class VerifyEmailUseCase(private val auth : FirebaseAuth) {
    suspend operator fun invoke(): Boolean {
        auth.currentUser?.let {
            it.reload().await()
            if (it.isEmailVerified) {
                return true
            }
        }
        return false
    }
}