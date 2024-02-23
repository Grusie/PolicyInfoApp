package com.grusie.policyinfo.di

import com.grusie.data.db.PolicyInfoDao
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
    fun providesPolicyRemoteSource(source : PolicyRemoteSourceImpl): PolicyRemoteSource {
        return source
    }
    @Provides
    fun provideLocalDataSource(policyInfoDao: PolicyInfoDao): PolicyLocalDataSource = PolicyLocalDataSourceImpl(policyInfoDao = policyInfoDao)
}