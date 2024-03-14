package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.AuthUseCases.AuthUseCases
import com.grusie.presentation.uiState.SignUpUiState
import com.grusie.presentation.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val useCases: AuthUseCases) : BaseViewModel() {
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

    fun changeVerifyChecked(verifyChecked: Boolean) {
        viewModelScope.launch {
            _verifyChecked.emit(verifyChecked)
        }
    }

    fun sendEmail() {
        if(_idText.value.trim().isEmpty()){
            setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_CODE_EMPTY))
        }
        else if(!isValidEmail()){
            setSignUpUiState(SignUpUiState.Alert(Constant.ERROR_CODE_FORMAT))
        } else {
            viewModelScope.launch {
                setSignUpUiState(SignUpUiState.Loading)
                try {
                    useCases.emailSendUseCase(_idText.value)
                    changeVerifyChecked(false)
                    setSignUpUiState(SignUpUiState.SuccessSendEmail)
                } catch (e: Exception) {
                    setSignUpUiState(SignUpUiState.Error(e))
                }
            }
        }
    }

    private fun isValidEmail(): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(_idText.value)
    }

    private fun setSignUpUiState(state: SignUpUiState) {
        viewModelScope.launch {
            _signUpUiState.emit(state)
        }
    }
}