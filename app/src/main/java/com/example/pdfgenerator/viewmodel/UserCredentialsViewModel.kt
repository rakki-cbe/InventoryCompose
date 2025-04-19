package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.data.RESUTLT_USECASE_SUCCESS
import com.example.pdfgenerator.data.model.CurrentUserData
import com.example.pdfgenerator.data.model.UserInfo
import com.example.pdfgenerator.data.network.NetworkResult
import com.example.pdfgenerator.data.network.UserCredentials
import com.example.pdfgenerator.data.network.usecase.CreateUserUseCase
import com.example.pdfgenerator.extension.filterNull
import com.example.pdfgenerator.ui.user.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import javax.inject.Inject

@HiltViewModel
class UserCredentialsViewModel @Inject constructor(
    private val userUseCase: CreateUserUseCase,
    val userCredentials: UserCredentials
) : ViewModel() {

    private val _result = MutableStateFlow(UseCaseResult<UserInfo>())
    val result = _result.asStateFlow()


    private val _activeUserStatus = MutableStateFlow<CurrentUserData?>(null)
    val activeUserStatus = _activeUserStatus.asStateFlow()


    fun clearResultData() {
        _result.value = UseCaseResult()
    }

    fun storeCredential(data: UserInfo?) {
        viewModelScope.launch(Dispatchers.Main) {
            data?.let {
                val credential =
                    Credentials.basic(data.userName.filterNull(""), data.password.filterNull(""))
                val data = CurrentUserData(
                    name = data.name,
                    userName = data.userName,
                    credential = credential
                )
                userUseCase.saveAuthDataToDB(data)
                userCredentials.userCredentialsInfo = credential
                _activeUserStatus.value = data
            }
        }
    }

    fun loginUser(userName: String, passWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = userUseCase.loginUser(userName, passWord)) {
                is NetworkResult.Success -> {
                    _result.value = UseCaseResult<UserInfo>().apply {
                        this.data = response.data
                        this.resultCode = RESUTLT_USECASE_SUCCESS
                        this.isLoading = false
                    }


                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Exception -> {

                }
            }
        }
    }

    fun loadCurrentUserCredentials() {
        viewModelScope.launch {
            val result = userUseCase.getActiveUserData()
            result?.let {
                userCredentials.userCredentialsInfo = result.credential
                _activeUserStatus.value = it
            }
        }
    }
}