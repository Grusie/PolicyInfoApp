package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.presentation.eventState.SignInEventState
import com.grusie.presentation.uiState.SignInUiState
import com.grusie.presentation.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    BaseViewModel() {
    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Init)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    private val _signInEventState = MutableSharedFlow<SignInEventState>()
    val signInEventState: SharedFlow<SignInEventState> = _signInEventState

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
        when {
            _idText.value.isEmpty() || _pwText.value.isEmpty() -> { //필수값이 입력되지 않았을 때
                setSignInEventState(SignInEventState.Alert(Constant.ERROR_CODE_EMPTY))
            }

            !isValidEmail() -> {  //이메일 패턴이 맞지 않을 때
                setSignInEventState(SignInEventState.Alert(Constant.ERROR_CODE_FORMAT))
            }

            !isValidPassword() -> { //비밀번호 패턴이 맞지 않을 때
                setSignInEventState(SignInEventState.Alert(Constant.ERROR_PASSWORD_FORMAT))
            }

            else -> {
                viewModelScope.launch {

                    _signInUiState.emit(SignInUiState.Loading)
                    try {
                        emailUseCases.signInEmailUseCase(id = _idText.value, pw = _pwText.value)
                        _signInUiState.emit(SignInUiState.SuccessSignIn)
                    } catch (e: Exception) {
                        setSignInEventState(SignInEventState.Error(e))
                        setSignInUiState(SignInUiState.Init)
                    }
                }
            }
        }
    }

    private fun setSignInEventState(state: SignInEventState) {
        viewModelScope.launch {
            _signInEventState.emit(state)
        }
    }

    private fun setSignInUiState(state: SignInUiState) {
        viewModelScope.launch {
            _signInUiState.emit(state)
        }
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(_idText.value)
    }

    private fun isValidPassword(): Boolean {
        val pattern =
            Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()-_+=])[A-Za-z\\d!@#\$%^&*()-_+=]{8,}\$")
        return pattern.matches(_pwText.value)
    }

}