package com.grusie.policyinfo.di

import android.app.Application
import androidx.room.Room
import com.grusie.data.db.PolicyInfoDB
import com.grusie.data.db.PolicyInfoDao
import com.grusie.data.db.PolicyRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(app: Application): PolicyInfoDB =
        Room.databaseBuilder(app, PolicyInfoDB::class.java, "policy_info_db").fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePolicyDao(policyInfoDB: PolicyInfoDB) : PolicyInfoDao = policyInfoDB.policyDao()

    @Provides
    fun providePolicyRemoteKeysDao(policyInfoDB: PolicyInfoDB) : PolicyRemoteKeysDao = policyInfoDB.policyRemoteKeysDao()
}