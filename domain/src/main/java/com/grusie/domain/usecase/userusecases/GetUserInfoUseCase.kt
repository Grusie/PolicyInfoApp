package com.grusie.domain.usecase.userusecases

import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserInfoUseCase(
    private val repository: UserInfoRepository,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(): Flow<UserInfo?> {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null)
            repository.getUserInfo(uid = currentUser.uid)
        else flow { emit(null) }
    }
}