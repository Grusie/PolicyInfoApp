package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grusie.domain.model.UserInfo
import com.grusie.domain.usecase.authusecases.AuthUseCases
import com.grusie.domain.usecase.userusecases.UserInfoUseCases
import com.grusie.presentation.eventState.ManageAuthEventState
import com.grusie.presentation.uiState.ManageAuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val userInfoUseCases: UserInfoUseCases
) : ViewModel() {
    private val emailAuthUseCases = authUseCases.emailAuthUseCases

    private val _manageAuthUiState: MutableStateFlow<ManageAuthUiState> =
        MutableStateFlow(ManageAuthUiState.Init)
    val manageAuthUiState: StateFlow<ManageAuthUiState> = _manageAuthUiState

    private val _manageAuthEventState: MutableSharedFlow<ManageAuthEventState> = MutableSharedFlow()
    val manageAuthEventState: SharedFlow<ManageAuthEventState> = _manageAuthEventState

    private val _nicknameText = MutableStateFlow("")
    val nicknameText: StateFlow<String> = _nicknameText

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    fun signOut() {
        viewModelScope.launch {
            try {
                _manageAuthUiState.emit(ManageAuthUiState.Loading)
                emailAuthUseCases.signOutEmailUseCase()
                _manageAuthUiState.emit(ManageAuthUiState.SuccessSignOut)
            } catch (e: Exception) {
                setManageAuthEventState(ManageAuthEventState.Error(e))
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            userInfoUseCases.getUserInfoUseCase().collectLatest { userInfo ->
                _userInfo.emit(userInfo)
                _userInfo.value?.let { _nicknameText.emit(it.nickname) }
            }
        }
    }

    fun changeNicknameText(nickname: String) {
        viewModelScope.launch {
            _nicknameText.emit(nickname)
        }
    }

    fun saveNickname() {
        viewModelScope.launch {
            _manageAuthUiState.emit(ManageAuthUiState.Loading)
            try {
                _userInfo.value?.let {
                    userInfoUseCases.updateUserInfoUseCase(
                        it.copy(
                            nickname = _nicknameText.value
                        )
                    )
                }
                setManageAuthEventState(ManageAuthEventState.SuccessChangeNickname)
            }catch (e:Exception){
                setManageAuthEventState(ManageAuthEventState.Error(e))
            }
        }
    }

    private fun setManageAuthUiState(state : ManageAuthUiState){
        viewModelScope.launch {
            _manageAuthUiState.emit(state)
        }
    }
    private fun setManageAuthEventState(state : ManageAuthEventState){
        viewModelScope.launch {
            setManageAuthUiState(ManageAuthUiState.Init)
            _manageAuthEventState.emit(state)
        }
    }
}