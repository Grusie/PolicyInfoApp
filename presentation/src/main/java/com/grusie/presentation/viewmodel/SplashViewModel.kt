package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.domain.model.LocalAuth
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.presentation.eventState.SplashEventState
import com.grusie.presentation.uiState.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _splashUiState: MutableStateFlow<SplashUiState> =
        MutableStateFlow(SplashUiState.Loading)
    val splashUiState: StateFlow<SplashUiState> = _splashUiState

    private val _splashEventState: MutableSharedFlow<SplashEventState> = MutableSharedFlow()
    val splashEventState: SharedFlow<SplashEventState> = _splashEventState

    private val _localAuth: MutableStateFlow<LocalAuth?> = MutableStateFlow(null)

    init {
        authEmailLogIn()
    }

    private fun authEmailLogIn() {
        viewModelScope.launch {
            try {
                delay(1000)
                localAuthUseCases.getLocalAuthUseCase().collectLatest { _localAuth.emit(it) }
                val userInfo = _localAuth.value?.let {
                    emailAuthUseCases.signInEmailUseCase(it)
                }

                setSplashUiState(SplashUiState.SuccessSignIn(userInfo))
            } catch (e: Exception) {
                Log.e("confirm error : ", "${e.message}")
                setSplashEventState(SplashEventState.Error(e))
                setSplashUiState(SplashUiState.Init)
            }
        }
    }

    private fun setSplashUiState(state: SplashUiState) {
        viewModelScope.launch {
            _splashUiState.emit(state)
        }
    }
    private fun setSplashEventState(state: SplashEventState) {
        viewModelScope.launch {
            _splashEventState.emit(state)
        }
    }
}