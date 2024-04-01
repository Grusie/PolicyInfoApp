package com.grusie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.grusie.domain.model.UserInfo
import com.grusie.domain.usecase.userusecases.UserInfoUseCases
import com.grusie.presentation.eventState.MyPageEventState
import com.grusie.presentation.uiState.MyPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoUseCases: UserInfoUseCases
) : BaseViewModel() {
    private val _myPageUiState = MutableStateFlow<MyPageUiState>(MyPageUiState.Init)
    val myPageUiState: StateFlow<MyPageUiState> = _myPageUiState

    private val _myPageEventState = MutableSharedFlow<MyPageEventState>()
    val myPageEventState: SharedFlow<MyPageEventState> = _myPageEventState

    private val _userInfo = MutableStateFlow<UserInfo?>(null)

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                setMyPageUiState(MyPageUiState.Loading)
                userInfoUseCases.getUserInfoUseCase().collectLatest {
                    _userInfo.emit(it)
                    setMyPageUiState(MyPageUiState.SuccessLogIn(_userInfo.value))
                }
            } catch (e: Exception) {
                setMyPageUiState(MyPageUiState.Init)
                setMyPageEventState(MyPageEventState.Error(e))
            }
        }
    }

    private fun setMyPageUiState(state: MyPageUiState) {
        viewModelScope.launch {
            _myPageUiState.emit(state)
        }
    }

    private fun setMyPageEventState(state: MyPageEventState) {
        viewModelScope.launch {
            _myPageEventState.emit(state)
        }
    }
}