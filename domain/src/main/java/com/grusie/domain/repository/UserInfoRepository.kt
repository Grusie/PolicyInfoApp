package com.grusie.domain.repository

import com.grusie.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {
    suspend fun getUserInfo(id: String) : Flow<UserInfo>
    suspend fun deleteUserInfo(userInfo: UserInfo)
    suspend fun createUserInfo(userInfo: UserInfo)
    suspend fun updateUserInfo(userInfo: UserInfo)
}