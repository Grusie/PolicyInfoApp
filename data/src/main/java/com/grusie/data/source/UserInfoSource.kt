package com.grusie.data.source

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.grusie.data.model.UserInfoData
import com.grusie.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface UserInfoSource {
    suspend fun getUserInfo(uid: String): Flow<UserInfoData?>
    suspend fun deleteUserInfo(userInfo: UserInfo)
    suspend fun updateUserInfo(userInfo: UserInfo)
    suspend fun createUserInfo(userInfo: UserInfo)
}

class UserInfoSourceImpl @Inject constructor(private val fireStore: FirebaseFirestore) :
    UserInfoSource {
    override suspend fun getUserInfo(uid: String): Flow<UserInfoData?> {
        return flow {
            val myData = fireStore.collection("users").document(uid).get().await()
            Log.d("confirm getUserInfo myData : ", "$myData")
            emit(myData.toObject<UserInfoData>())
            Log.d("confirm getUserInfo2 myData : ", "${myData.toObject<UserInfoData>()}")
        }
    }

    override suspend fun deleteUserInfo(userInfo: UserInfo) {
        fireStore.collection("users").document(userInfo.uid).delete().await()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        fireStore.collection("users").document(userInfo.uid).set(userInfo).await()
    }

    override suspend fun createUserInfo(userInfo: UserInfo) {
        fireStore.collection("users").document(userInfo.uid).set(userInfo)
        Log.d("confirm userInfoData : ", "$userInfo")
    }
}