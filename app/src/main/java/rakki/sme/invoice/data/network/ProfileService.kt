package rakki.sme.invoice.data.network

import rakki.sme.invoice.data.model.Profile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileService {
    @GET("profile/get")
    suspend fun getProfileForCompany(@Query("companyName") name: String): Profile

    @POST("profile/create")
    suspend fun saveProfile(@Body profile: Profile): Profile
}