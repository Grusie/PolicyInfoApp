package com.grusie.data.source

import android.content.SharedPreferences
import android.util.Log
import com.grusie.data.model.LocalAuthData
import com.grusie.domain.model.LocalAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalAuthSource {
    suspend fun createLocalAuth(localAuth: LocalAuth)
    suspend fun getLocalAuth(): Flow<LocalAuthData>
    suspend fun deleteLocalAuth()
}

class LocalAuthSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    LocalAuthSource {
    companion object {
        private const val KEY_UID = "uid"
        private const val KEY_ID = "id"
        private const val KEY_PW = "pw"
    }

    override suspend fun createLocalAuth(localAuth: LocalAuth) {
        sharedPreferences.edit().apply {
            deleteLocalAuth()
            putString(KEY_UID, localAuth.uid)
            putString(KEY_ID, localAuth.id)
            putString(KEY_PW, localAuth.pw)
            apply()
        }
    }

    override suspend fun getLocalAuth(): Flow<LocalAuthData> {
        return flow {
            val uid = sharedPreferences.getString(KEY_UID, null)
            val id = sharedPreferences.getString(KEY_ID, null)
            val pw = sharedPreferences.getString(KEY_PW, null)
            if (!uid.isNullOrEmpty() && !id.isNullOrEmpty() && !pw.isNullOrEmpty()) {
                emit(LocalAuthData(uid, id, pw))
            }
            Log.d("confirm uid, id, pw : ", "uid : $uid, id : $id, pw : $pw")
        }
    }

    override suspend fun deleteLocalAuth() {
        sharedPreferences.edit().apply {
            remove(KEY_UID)
            remove(KEY_ID)
            remove(KEY_PW)
            apply()
        }
    }
}
