package com.grusie.data.model

import com.grusie.domain.model.UserInfo

data class UserInfoData(
    val uid: String = "",
    val id: String = "",
    val nickname: String = ""
)

fun UserInfoData.toUserInfo(): UserInfo {
    return UserInfo(
        uid = uid,
        id = id,
        nickname = nickname
    )
}