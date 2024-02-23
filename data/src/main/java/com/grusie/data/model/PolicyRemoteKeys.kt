package com.grusie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "policy_remote_keys")
data class PolicyRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prePage: Int?,
    val nextPage: Int?
)