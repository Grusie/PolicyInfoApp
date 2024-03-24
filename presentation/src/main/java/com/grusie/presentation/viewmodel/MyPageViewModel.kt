package com.grusie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.grusie.domain.model.UserInfo
import com.grusie.domain.usecase.authusecases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val firebaseAuth: FirebaseAuth
) :
    BaseViewModel() {
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    init {
        getUserInfo()
    }
    fun getUserInfo() {
        viewModelScope.launch {
            try{
                if(firebaseAuth.currentUser != null){
                    _userInfo.emit(UserInfo(nickName = "Grusie"))
                } else _userInfo.emit(null)
            }catch (e:Exception){
                Log.e("confirm getUserInfo error : ", "${e.message}")
            }
        }
    }
}