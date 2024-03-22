package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.presentation.uiState.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) : BaseViewModel() {
    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Empty)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    private val _idText = MutableStateFlow("")
    val idText: StateFlow<String> = _idText

    private val _pwText = MutableStateFlow("")
    val pwText: StateFlow<String> = _pwText

    private val emailUseCases = authUseCases.emailAuthUseCases

    fun changeIdText(idText: String) {
        viewModelScope.launch {
            _idText.emit(idText)
        }
    }

    fun changePwText(pwText: String) {
        viewModelScope.launch {
            _pwText.emit(pwText)
        }
    }

    fun emailSignIn() {
        viewModelScope.launch {
            _signInUiState.emit(SignInUiState.Loading)
            try{
                emailUseCases.signInEmailUseCase(id = _idText.value, pw = _pwText.value)
                _signInUiState.emit(SignInUiState.SuccessSignIn)
            } catch (e:Exception){
                _signInUiState.emit(SignInUiState.Error(e))
            }
        }
    }
}