package com.example.pdfgenerator.data.network.usecase

import com.example.pdfgenerator.data.model.CurrentUserData
import com.example.pdfgenerator.data.model.UserInfo
import com.example.pdfgenerator.data.network.APIResonseHanlder
import com.example.pdfgenerator.data.network.NetworkResult
import com.example.pdfgenerator.data.network.UserCredentialsService
import com.example.pdfgenerator.data.repository.UserCredentialsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    val useCase: UserCredentialsService,
    val repo: UserCredentialsRepo
) :
    APIResonseHanlder {
    suspend fun getUser(user: UserInfo): NetworkResult<UserInfo> =
        withContext(Dispatchers.IO) {
            handleApi { useCase.createUser(user) }
        }

    suspend fun loginUser(useName: String, password: String): NetworkResult<UserInfo> =
        withContext(Dispatchers.IO) {
            handleApi {
                useCase.loginUser(
                    UserInfo(
                        name = "",
                        userName = useName,
                        password = password
                    )
                )
            }
        }

    suspend fun saveAuthDataToDB(activeUserData: CurrentUserData) {
        withContext(Dispatchers.IO) {
            repo.deleteAll()
            repo.saveUserData(activeUserData)
        }
    }

    suspend fun getActiveUserData(): CurrentUserData? {
        return withContext(Dispatchers.IO) {
            repo.getAlluser().firstOrNull()
        }
    }

}