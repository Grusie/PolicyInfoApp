package com.grusie.domain.usecase.authusecases.email

import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.BuildConfig
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.utils.Utils
import kotlinx.coroutines.tasks.await

class SendEmailUseCase(private val auth: FirebaseAuth, private val localAuthRepository: LocalAuthRepository) {
    suspend operator fun invoke(email: String) {
        try {
            // 난수 비밀번호 생성
            val randomData =
                Utils.generateRandomBytes(16)
            // AES 키
            val aesKey = BuildConfig.PASSWORD_AES_KEY
            // 데이터 암호화
            val stringEncryptedPassword = Utils.encryptData(randomData, aesKey)

            auth.createUserWithEmailAndPassword(email, stringEncryptedPassword).await()
            val uid = auth.currentUser!!.uid

            //비정상적인 로그인(이메일 인증을 보내기만 하고, 회원가입을 완료하지 않았을 경우)시도 시 삭제하기 위함)
            val tempPw = Utils.encryptData(stringEncryptedPassword.toByteArray(Charsets.UTF_8), BuildConfig.PASSWORD_AES_KEY)
            localAuthRepository.createLocalAuth(LocalAuth(uid = uid,id = email, pw = tempPw))

            val user = auth.currentUser!!

            user.sendEmailVerification().await()
        } catch (e: Exception) {
            auth.currentUser?.delete()
            localAuthRepository.deleteLocalAuth()
            throw e
        }
    }
}