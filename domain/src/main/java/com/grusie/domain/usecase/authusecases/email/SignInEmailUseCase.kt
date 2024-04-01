package com.grusie.domain.usecase.authusecases.email

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.BuildConfig
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.UserInfoRepository
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await

class SignInEmailUseCase(
    private val localAuthRepository: LocalAuthRepository,
    private val userInfoRepository: UserInfoRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(id: String, pw: String) {
        try {
            auth.signInWithEmailAndPassword(id, pw).await()
            val uid = auth.currentUser!!.uid

            val encryptedPassword =
                Utils.encryptData(pw.toByteArray(Charsets.UTF_8), BuildConfig.PASSWORD_AES_KEY)
            localAuthRepository.createLocalAuth(
                LocalAuth(
                    uid = uid,
                    id = id,
                    pw = encryptedPassword
                )
            )
        } catch (e: Exception) {
            //로그인에 실패 했을 시
            auth.signOut()
            localAuthRepository.deleteLocalAuth()
            Log.e("confirm signInEmail Error : ", "${e.message}")
            throw e
        }
    }

    suspend operator fun invoke(localAuth: LocalAuth): UserInfo? {
        try {
            val decryptedPassword = Utils.decryptData(localAuth.pw, BuildConfig.PASSWORD_AES_KEY)
            auth.signInWithEmailAndPassword(localAuth.id, decryptedPassword).await()
            val uid = auth.currentUser!!.uid

            var userInfo: UserInfo? = null

            userInfoRepository.getUserInfo(uid)
                .collectLatest {//비정상적인 로그인 발생 시(회원DB가 없다면) 로그인을 막으면서, 삭제하기 위함
                    Log.d("confirm signIn myData : ", "$it")
                    userInfo = it
                }

            if (userInfo == null) {
                auth.currentUser?.delete()
                auth.signOut()
                localAuthRepository.deleteLocalAuth()
            }

            return userInfo
        } catch (e: Exception) {
            Log.e("confirm signInEmail Error : ", "${e.message}")
            throw e
        }
    }
}