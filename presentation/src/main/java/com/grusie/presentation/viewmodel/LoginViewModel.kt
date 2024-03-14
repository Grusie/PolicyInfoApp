package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.AuthUseCases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val useCase: AuthUseCases) : BaseViewModel() {
    private val _idText = MutableStateFlow("")
    val idText: StateFlow<String> = _idText

    private val _pwText = MutableStateFlow("")
    val pwText: StateFlow<String> = _pwText

    fun changeIdText(idText : String){
        viewModelScope.launch {
            _idText.emit(idText)
        }
    }
    fun changePwText(pwText : String){
        viewModelScope.launch {
            _pwText.emit(pwText)
        }
    }

    fun emailLogin(){

    }
}