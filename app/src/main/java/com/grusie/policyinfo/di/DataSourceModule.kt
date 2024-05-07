package com.grusie.policyinfo.di

import com.grusie.data.source.FavoritePolicySource
import com.grusie.data.source.FavoritePolicySourceImpl
import com.grusie.data.source.LocalAuthSource
import com.grusie.data.source.LocalAuthSourceImpl
import com.grusie.data.source.PolicyLocalDataSource
import com.grusie.data.source.PolicyLocalDataSourceImpl
import com.grusie.data.source.PolicyRemoteSource
import com.grusie.data.source.PolicyRemoteSourceImpl
import com.grusie.data.source.UserInfoSource
import com.grusie.data.source.UserInfoSourceImpl
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
    fun provideLocalDataSource(source: PolicyLocalDataSourceImpl): PolicyLocalDataSource {
        return source
    }

    @Provides
    @Singleton
    fun provideLocalAuthSource(source: LocalAuthSourceImpl): LocalAuthSource {
        return source
    }

    @Provides
    @Singleton
    fun provideUserInfoSource(source: UserInfoSourceImpl): UserInfoSource {
        return source
    }

    @Provides
    @Singleton
    fun provideFavoriteDataSource(source: FavoritePolicySourceImpl) : FavoritePolicySource {
        return source
    }
}