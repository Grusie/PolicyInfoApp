package com.grusie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.presentation.uiState.ManageAuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAuthViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    private val emailAuthUseCases = authUseCases.emailAuthUseCases

    private val _manageAuthUiState: MutableStateFlow<ManageAuthUiState> = MutableStateFlow(ManageAuthUiState.Empty)
    val manageAuthUiState : StateFlow<ManageAuthUiState> = _manageAuthUiState

    fun signOut(){
        viewModelScope.launch {
            try {
                _manageAuthUiState.emit(ManageAuthUiState.Loading)
                emailAuthUseCases.signOutEmailUseCase()
                _manageAuthUiState.emit(ManageAuthUiState.SuccessSignOut)
            }catch (e:Exception){
                _manageAuthUiState.emit(ManageAuthUiState.Error(e))
            }
        }
    }
}