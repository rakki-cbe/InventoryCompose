package rakki.sme.invoice.data.network.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.CurrentUserData
import rakki.sme.invoice.data.model.UserInfo
import rakki.sme.invoice.data.network.APIResonseHanlder
import rakki.sme.invoice.data.network.NetworkResult
import rakki.sme.invoice.data.network.UserCredentialsService
import rakki.sme.invoice.data.repository.UserCredentialsRepo
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