package com.grusie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grusie.data.model.PolicyItem
import com.grusie.data.model.PolicyRemoteKeys


@Database(
    entities = [PolicyItem::class, PolicyRemoteKeys::class],
    version = 1
)
abstract class PolicyInfoDB : RoomDatabase() {
    abstract fun policyDao(): PolicyInfoDao
    abstract fun policyRemoteKeysDao(): PolicyRemoteKeysDao
}