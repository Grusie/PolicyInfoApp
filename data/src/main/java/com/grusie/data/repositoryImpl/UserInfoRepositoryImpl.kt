package com.grusie.data.repositoryImpl

import com.grusie.data.model.toUserInfo
import com.grusie.data.source.UserInfoSource
import com.grusie.domain.model.UserInfo
import com.grusie.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(private val source: UserInfoSource): UserInfoRepository {
    override suspend fun getUserInfo(uid: String): Flow<UserInfo?> {
        return source.getUserInfo(uid).map { it?.toUserInfo() }
    }

    override suspend fun deleteUserInfo(userInfo: UserInfo) {
        source.deleteUserInfo(userInfo = userInfo)
    }

    override suspend fun createUserInfo(userInfo: UserInfo) {
        source.createUserInfo(userInfo)
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        source.updateUserInfo(userInfo = userInfo)
    }
}