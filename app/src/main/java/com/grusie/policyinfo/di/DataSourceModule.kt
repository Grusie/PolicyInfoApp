package com.grusie.policyinfo.di

import android.content.SharedPreferences
import com.grusie.data.db.policyinfo.PolicyInfoDao
import com.grusie.data.source.LocalAuthSource
import com.grusie.data.source.LocalAuthSourceImpl
import com.grusie.data.source.PolicyLocalDataSource
import com.grusie.data.source.PolicyLocalDataSourceImpl
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.data.source.PolicyRemoteSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun providesPolicyRemoteSource(source: PolicyRemoteSourceImpl): PolicyRemoteSource {
        return source
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(policyInfoDao: PolicyInfoDao): PolicyLocalDataSource =
        PolicyLocalDataSourceImpl(policyInfoDao = policyInfoDao)

    @Provides
    @Singleton
    fun provideLocalAuthSource(sharedPreferences: SharedPreferences): LocalAuthSource =
        LocalAuthSourceImpl(sharedPreferences = sharedPreferences)
}