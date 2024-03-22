package com.grusie.domain.usecase.authusecases.email

import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.BuildConfig
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.tasks.await

class SendEmailUseCase(private val auth: FirebaseAuth) {
    suspend operator fun invoke(email: String) {
        try {
            // 난수 비밀번호 생성
            val randomData =
                Utils.generateRandomBytes(16)
            // AES 키
            val aesKey = BuildConfig.PASSWORD_AES_KEY
            // 데이터 암호화
            val encryptedPassword = Utils.encryptData(randomData, aesKey)

            auth.createUserWithEmailAndPassword(email, encryptedPassword).await()
            val user = auth.currentUser!!

            user.sendEmailVerification().await()
        } catch (e: Exception) {
            auth.currentUser?.delete()
            throw e
        }
    }
}