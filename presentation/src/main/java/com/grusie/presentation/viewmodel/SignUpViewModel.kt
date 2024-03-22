package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.presentation.uiState.SignUpUiState
import com.grusie.presentation.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel() {
    private val emailAuthUseCases = authUseCases.emailAuthUseCases

    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Empty)

    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState
    private val _idText = MutableStateFlow("")

    val idText: StateFlow<String> = _idText
    private val _pwText = MutableStateFlow("")

    val pwText: StateFlow<String> = _pwText
    private val _verifyChecked = MutableStateFlow<Boolean?>(null)

    val verifyChecked: StateFlow<Boolean?> = _verifyChecked

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

    fun changeVerifyChecked(verifyChecked: Boolean?) {
        viewModelScope.launch {
            _verifyChecked.emit(verifyChecked)
        }
    }

    fun signUpEmail() {
        when {
            _idText.value.isEmpty() || _pwText.value.isEmpty() -> { //필수값이 입력되지 않았을 때
                setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_CODE_EMPTY))
            }

            _verifyChecked.value != true -> {   //이메일 인증이 되지 않았을 때
                setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_EMAIL_UNVERIFIED))
            }

            !isValidPassword() -> { //비밀번호 패턴이 맞지 않을 때
                setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_PASSWORD_FORMAT))
            }

            else -> {   //회원가입 로직
                viewModelScope.launch {
                    try {
                        setSignUpUiState(SignUpUiState.Loading)
                        emailAuthUseCases.signUpEmailUseCase(
                            email = _idText.value,
                            password = _pwText.value
                        )

                        setSignUpUiState(SignUpUiState.SuccessSignUp)
                    } catch (e: Exception) {
                        setSignUpUiState(SignUpUiState.Error(e))
                    }
                }
            }
        }
    }

    fun sendEmail() {
        if (_idText.value.trim().isEmpty()) {
            setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_CODE_EMPTY))
        } else if (!isValidEmail()) {
            setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_CODE_FORMAT))
        } else {
            viewModelScope.launch {
                setSignUpUiState(SignUpUiState.Loading)
                try {
                    emailAuthUseCases.deleteEmailUseCase()
                    emailAuthUseCases.sendEmailUseCase(_idText.value)
                    changeVerifyChecked(false)
                    setSignUpUiState(SignUpUiState.SuccessSendEmail)
                } catch (e: Exception) {
                    setSignUpUiState(SignUpUiState.Error(e))
                }
            }
        }
    }

    suspend fun isVerified() {
        if (emailAuthUseCases.verifyEmailUseCase()) {
            changeVerifyChecked(true)
        }
        return
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

    private fun setSignUpUiState(state: SignUpUiState) {
        viewModelScope.launch {
            _signUpUiState.emit(state)
        }
    }
}