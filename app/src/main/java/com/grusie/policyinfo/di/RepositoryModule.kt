package com.grusie.policyinfo.di

import com.grusie.data.repositoryImpl.EmailAuthRepositoryImpl
import com.grusie.data.repositoryImpl.PolicyRepositoryImpl
import com.grusie.domain.repository.EmailAuthRepository
import com.grusie.domain.repository.PolicyRepository
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
    fun providePolicyRepository(repository: PolicyRepositoryImpl) : PolicyRepository{
        return repository
    }

    @Singleton
    @Provides
    fun provideEmailAuthRepository(repository: EmailAuthRepositoryImpl) : EmailAuthRepository {
        return repository
    }
}