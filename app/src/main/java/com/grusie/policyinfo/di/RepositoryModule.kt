package com.grusie.policyinfo.di

import com.grusie.data.repositoryImpl.FavoritePolicyRepositoryImpl
import com.grusie.data.repositoryImpl.LocalAuthRepositoryImpl
import com.grusie.data.repositoryImpl.PolicyRepositoryImpl
import com.grusie.data.repositoryImpl.UserInfoRepositoryImpl
import com.grusie.data.source.FavoritePolicySource
import com.grusie.domain.repository.FavoritePolicyRepository
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.PolicyRepository
import com.grusie.domain.repository.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePolicyRepository(repository: PolicyRepositoryImpl): PolicyRepository {
        return repository
    }

    @Singleton
    @Provides
    fun provideLocalAuthRepository(repository: LocalAuthRepositoryImpl): LocalAuthRepository {
        return repository
    }

    @Singleton
    @Provides
    fun provideUserInfoRepository(repository: UserInfoRepositoryImpl): UserInfoRepository {
        return repository
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(repository: FavoritePolicyRepositoryImpl) : FavoritePolicyRepository {
        return repository
    }
}