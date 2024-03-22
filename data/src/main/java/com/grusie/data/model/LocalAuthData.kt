package com.grusie.data.model

import com.grusie.domain.model.LocalAuth

data class LocalAuthData(
    val uid: String = "",
    val id: String = "",
    val pw: String = ""
)

fun LocalAuthData.toLocalAuth(): LocalAuth {
    return LocalAuth(
        uid = uid,
        id = id,
        pw = pw
    )
}