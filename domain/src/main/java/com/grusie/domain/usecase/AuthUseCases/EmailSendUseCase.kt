package com.grusie.domain.usecase.AuthUseCases

import com.google.firebase.Firebase
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.grusie.domain.BuildConfig
import com.grusie.domain.repository.EmailAuthRepository
import com.grusie.domain.utils.Constant
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.tasks.await

class EmailSendUseCase(private val repository: EmailAuthRepository) {
    lateinit var auth: FirebaseAuth
    suspend operator fun invoke(email: String) {
        try {
            auth = Firebase.auth

            // 난수 비밀번호 생성
            val randomData =
                Utils.generateRandomBytes(16)
            // AES 키
            val aesKey = BuildConfig.PASSWORD_AES_KEY
            // 데이터 암호화
            val encryptedPassword = Utils.encryptData(randomData, aesKey)


            auth.createUserWithEmailAndPassword(email, encryptedPassword).await()
            val user = auth.currentUser!!
            val url =  BuildConfig.FIREBASE_DYNAMIC_LINK + Constant.VERIFY_PREFIX + "?uid=" + user.uid
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setAndroidPackageName("com.grusie.policyInfo", false, null)
                .build()

            user.sendEmailVerification(actionCodeSettings).await()

        } catch (e: Exception) {
            auth.currentUser?.delete()
            throw e
        }
    }
}