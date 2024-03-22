package com.grusie.domain.usecase.authusecases.email

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class DeleteEmailUseCase(
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke() {
        try {
            val user = auth.currentUser

            user?.delete()?.await()
        } catch (e: Exception) {
            Log.d("confirm deleteUser error : ", "${e.message}")
        }
    }
}