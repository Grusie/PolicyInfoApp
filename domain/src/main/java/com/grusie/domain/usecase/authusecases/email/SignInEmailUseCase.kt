package com.grusie.domain.usecase.authusecases.email

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.BuildConfig
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.tasks.await

class SignInEmailUseCase(
    private val localAuthRepository: LocalAuthRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(id: String, pw: String) {
        try {
            auth.signInWithEmailAndPassword(id, pw).await()
            val uid = auth.currentUser!!.uid

            val encryptedPassword = Utils.encryptData(pw.toByteArray(Charsets.UTF_8), BuildConfig.PASSWORD_AES_KEY)
            localAuthRepository.createLocalAuth(LocalAuth(uid = uid, id = id, pw = encryptedPassword))
        } catch (e: Exception) {
            //로그인에 실패 했을 시
            auth.signOut()
            localAuthRepository.deleteLocalAuth()
            Log.e("confirm signInEmail Error : ", "${e.message}")
            throw e
        }
    }

    suspend operator fun invoke(localAuth: LocalAuth) {
        try {
            val decryptedPassword = Utils.decryptData(localAuth.pw, BuildConfig.PASSWORD_AES_KEY)
            auth.signInWithEmailAndPassword(localAuth.id, decryptedPassword).await()
        } catch (e: Exception) {
            Log.e("confirm signInEmail Error : ", "${e.message}")
            throw e
        }
    }
}