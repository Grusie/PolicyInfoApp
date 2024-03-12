package com.grusie.policyinfo.di

import com.grusie.domain.repository.PolicyRepository
import com.grusie.domain.usecase.GetPolicyDetailUseCase
import com.grusie.domain.usecase.GetPolicyListUseCase
import com.grusie.domain.usecase.GetSearchPolicyListUseCase
import com.grusie.domain.usecase.PolicyUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providePolicyUseCases(repository: PolicyRepository) = PolicyUseCases(
        getPolicyDetailUseCase = GetPolicyDetailUseCase(repository),
        getSearchPolicyListUseCase = GetSearchPolicyListUseCase(repository),
        getPolicyListUseCase = GetPolicyListUseCase(repository)
    )
}
