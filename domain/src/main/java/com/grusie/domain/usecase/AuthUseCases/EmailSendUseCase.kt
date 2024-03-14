package com.grusie.domain.usecase.AuthUseCases

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.auth
import com.grusie.domain.repository.EmailAuthRepository
import kotlinx.coroutines.tasks.await

class EmailSendUseCase(private val repository: EmailAuthRepository) {
    suspend operator fun invoke(email: String) {
        val actionCodeSettings = actionCodeSettings {
            url = "https://www.naver.com/"
            // This must be true
            handleCodeInApp = true
            setAndroidPackageName(
                "com.grusie.policyInfoApp",
                true, // installIfNotAvailable
                "24", // minimumVersion
            )
        }

        try {
            Firebase.auth.sendSignInLinkToEmail(email, actionCodeSettings).await()
        } catch (e:Exception) {
            throw e
        }
    }
}