package com.grusie.domain.usecase.authusecases.email

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.BuildConfig
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.UserInfoRepository
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.tasks.await

class SignUpEmailUseCase(
    private val localAuthRepository: LocalAuthRepository,
    private val userInfoRepository: UserInfoRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(email: String, password: String) {
        try {
            auth.currentUser?.delete()?.await()     //이전에 임의로 생성한 계정을 삭제

            auth.createUserWithEmailAndPassword(email, password).await()

            val encryptedPassword = Utils.encryptData(
                password.toByteArray(Charsets.UTF_8),
                BuildConfig.PASSWORD_AES_KEY
            )

            val uid = auth.currentUser!!.uid

            userInfoRepository.createUserInfo(UserInfo(uid = uid, id = email))
            localAuthRepository.createLocalAuth(
                LocalAuth(
                    uid = uid,
                    id = email,
                    pw = encryptedPassword
                )
            )
        } catch (e: Exception) {
            auth.currentUser?.delete()?.await()
            Log.e("confirm signUp Error : ", "${e.message}")
            throw e
        }
    }
}