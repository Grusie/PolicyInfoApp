package com.grusie.policyinfo.di

import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.PolicyRepository
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.domain.usecase.authusecases.email.DeleteEmailUseCase
import com.grusie.domain.usecase.authusecases.email.EmailAuthUseCases
import com.grusie.domain.usecase.authusecases.email.SendEmailUseCase
import com.grusie.domain.usecase.authusecases.email.SignInEmailUseCase
import com.grusie.domain.usecase.authusecases.email.SignOutEmailUseCase
import com.grusie.domain.usecase.authusecases.email.SignUpEmailUseCase
import com.grusie.domain.usecase.authusecases.email.VerifyEmailUseCase
import com.grusie.domain.usecase.authusecases.local.CreateLocalAuthUseCase
import com.grusie.domain.usecase.authusecases.local.DeleteLocalAuthUseCase
import com.grusie.domain.usecase.authusecases.local.GetLocalAuthUseCase
import com.grusie.domain.usecase.authusecases.local.LocalAuthUseCases
import com.grusie.domain.usecase.policyusecases.GetPolicyDetailUseCase
import com.grusie.domain.usecase.policyusecases.GetPolicyListUseCase
import com.grusie.domain.usecase.policyusecases.GetSearchPolicyListUseCase
import com.grusie.domain.usecase.policyusecases.PolicyUseCases
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
    fun provideAuthUseCases(localAuthRepository: LocalAuthRepository, firebaseAuth: FirebaseAuth) =
        AuthUseCases(
            emailAuthUseCases = EmailAuthUseCases(
                signUpEmailUseCase = SignUpEmailUseCase(
                    localAuthRepository = localAuthRepository,
                    auth = firebaseAuth
                ),
                signInEmailUseCase = SignInEmailUseCase(
                    localAuthRepository = localAuthRepository,
                    auth = firebaseAuth
                ),
                signOutEmailUseCase = SignOutEmailUseCase(
                    auth = firebaseAuth
                ),
                sendEmailUseCase = SendEmailUseCase(auth = firebaseAuth),
                verifyEmailUseCase = VerifyEmailUseCase(auth = firebaseAuth),
                deleteEmailUseCase = DeleteEmailUseCase(auth = firebaseAuth)
            ),

            localAuthUseCases = LocalAuthUseCases(
                createLocalAuthUseCase = CreateLocalAuthUseCase(repository = localAuthRepository),
                deleteUserInfoUseCase = DeleteLocalAuthUseCase(repository = localAuthRepository),
                getLocalAuthUseCase = GetLocalAuthUseCase(repository = localAuthRepository)
            )
        )
}
