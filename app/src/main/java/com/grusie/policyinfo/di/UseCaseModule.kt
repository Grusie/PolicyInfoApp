package com.grusie.policyinfo.di

import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.repository.FavoritePolicyRepository
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.PolicyRepository
import com.grusie.domain.repository.UserInfoRepository
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
import com.grusie.domain.usecase.policyusecases.GetFavoritePolicyUseCase
import com.grusie.domain.usecase.policyusecases.GetPolicyDetailUseCase
import com.grusie.domain.usecase.policyusecases.GetPolicyListUseCase
import com.grusie.domain.usecase.policyusecases.GetSearchPolicyListUseCase
import com.grusie.domain.usecase.policyusecases.PolicyUseCases
import com.grusie.domain.usecase.userusecases.CreateUserInfoUseCase
import com.grusie.domain.usecase.userusecases.DeleteUserInfoUseCase
import com.grusie.domain.usecase.userusecases.GetUserInfoUseCase
import com.grusie.domain.usecase.userusecases.UpdateUserInfoUseCase
import com.grusie.domain.usecase.userusecases.UserInfoUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providePolicyUseCases(localAuthRepository: LocalAuthRepository ,policyRepository: PolicyRepository, favoritePolicyRepository: FavoritePolicyRepository) = PolicyUseCases(
        getPolicyDetailUseCase = GetPolicyDetailUseCase(policyRepository),
        getSearchPolicyListUseCase = GetSearchPolicyListUseCase(policyRepository),
        getPolicyListUseCase = GetPolicyListUseCase(policyRepository),
        getFavoritePolicyListUseCase = GetFavoritePolicyUseCase(localAuthRepository, policyRepository, favoritePolicyRepository)
    )

    @Provides
    fun provideAuthUseCases(localAuthRepository: LocalAuthRepository,userInfoRepository: UserInfoRepository, firebaseAuth: FirebaseAuth) =
        AuthUseCases(
            emailAuthUseCases = EmailAuthUseCases(
                signUpEmailUseCase = SignUpEmailUseCase(
                    localAuthRepository = localAuthRepository,
                    userInfoRepository = userInfoRepository,
                    auth = firebaseAuth
                ),
                signInEmailUseCase = SignInEmailUseCase(
                    localAuthRepository = localAuthRepository,
                    userInfoRepository = userInfoRepository,
                    auth = firebaseAuth
                ),
                signOutEmailUseCase = SignOutEmailUseCase(
                    localAuthRepository = localAuthRepository,
                    auth = firebaseAuth
                ),
                sendEmailUseCase = SendEmailUseCase(auth = firebaseAuth, localAuthRepository = localAuthRepository),
                verifyEmailUseCase = VerifyEmailUseCase(auth = firebaseAuth),
                deleteEmailUseCase = DeleteEmailUseCase(auth = firebaseAuth)
            ),

            localAuthUseCases = LocalAuthUseCases(
                createLocalAuthUseCase = CreateLocalAuthUseCase(repository = localAuthRepository),
                deleteUserInfoUseCase = DeleteLocalAuthUseCase(repository = localAuthRepository),
                getLocalAuthUseCase = GetLocalAuthUseCase(repository = localAuthRepository)
            )
        )

    @Provides
    fun provideUserInfoUseCases(userInfoRepository: UserInfoRepository, firebaseAuth: FirebaseAuth) : UserInfoUseCases{
        return UserInfoUseCases(
            getUserInfoUseCase = GetUserInfoUseCase(repository = userInfoRepository, firebaseAuth = firebaseAuth),
            deleteUserInfoUseCase = DeleteUserInfoUseCase(repository = userInfoRepository),
            createUserInfoUseCase = CreateUserInfoUseCase(repository = userInfoRepository),
            updateUserInfoUseCase = UpdateUserInfoUseCase(repository = userInfoRepository),
        )
    }
}
