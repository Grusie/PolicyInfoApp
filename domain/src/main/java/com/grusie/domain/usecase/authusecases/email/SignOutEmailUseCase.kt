package com.grusie.domain.usecase.authusecases.email

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.repository.LocalAuthRepository

class SignOutEmailUseCase(
    private val localAuthRepository: LocalAuthRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke() {
        try {
            auth.signOut()
            localAuthRepository.deleteLocalAuth()
        }catch (e:Exception){
            Log.e("confirm signOutEmail : ", "${e.message}")
            throw e
        }
    }
}