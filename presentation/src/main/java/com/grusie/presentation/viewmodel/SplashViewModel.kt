package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.domain.usecase.authusecases.local.LocalAuthUseCases
import com.grusie.presentation.uiState.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val emailAuthUseCases = authUseCases.emailAuthUseCases
    private val localAuthUseCases = authUseCases.localAuthUseCases

    private val _splashUiState: MutableStateFlow<SplashUiState> = MutableStateFlow(SplashUiState.Loading)
    val splashUiState: StateFlow<SplashUiState> = _splashUiState

    init {
        authEmailLogIn()
    }

    private fun authEmailLogIn() {
        viewModelScope.launch {
            try {
                localAuthUseCases.getLocalAuthUseCase().collectLatest { localAuth ->
                    emailAuthUseCases.signInEmailUseCase(localAuth)
                }

                _splashUiState.emit(SplashUiState.SuccessSignIn)
            }catch (e:Exception) {
                Log.e("confirm error : ", "${e.message}")
                _splashUiState.emit(SplashUiState.Error(e))
            }
        }
    }
}