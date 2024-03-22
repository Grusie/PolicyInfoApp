package com.grusie.presentation.viewmodel

import com.grusie.domain.model.UserInfo
import com.grusie.domain.usecase.authusecases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    BaseViewModel() {
    private val _userInfo = MutableStateFlow(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo
}