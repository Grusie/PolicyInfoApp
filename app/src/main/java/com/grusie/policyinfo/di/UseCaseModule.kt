package com.grusie.policyinfo.di

import com.grusie.domain.repository.EmailAuthRepository
import com.grusie.domain.repository.PolicyRepository
import com.grusie.domain.usecase.AuthUseCases.AuthUseCases
import com.grusie.domain.usecase.AuthUseCases.EmailLoginUseCase
import com.grusie.domain.usecase.AuthUseCases.EmailLogoutUseCase
import com.grusie.domain.usecase.AuthUseCases.EmailSendUseCase
import com.grusie.domain.usecase.AuthUseCases.EmailSignUpUseCase
import com.grusie.domain.usecase.PolicyUseCases.GetPolicyDetailUseCase
import com.grusie.domain.usecase.PolicyUseCases.GetPolicyListUseCase
import com.grusie.domain.usecase.PolicyUseCases.GetSearchPolicyListUseCase
import com.grusie.domain.usecase.PolicyUseCases.PolicyUseCases
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

    @Provides
    fun provideAuthUseCases(repository: EmailAuthRepository) = AuthUseCases(
        emailSignUpUseCase = EmailSignUpUseCase(repository),
        emailLoginUseCase = EmailLoginUseCase(repository),
        emailLogoutUseCase = EmailLogoutUseCase(repository),
        emailSendUseCase = EmailSendUseCase(repository)
    )
}
