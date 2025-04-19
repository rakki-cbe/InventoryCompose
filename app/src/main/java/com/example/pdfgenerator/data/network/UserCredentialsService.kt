package com.example.pdfgenerator.data.network

import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.model.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserCredentialsService {
    @GET("user/get")
    suspend fun getProfileForCompany(@Query("userName") name: String): Profile

    @POST("user/create")
    suspend fun createUser(@Body user: UserInfo): Response<UserInfo>

    @POST("user/login")
    suspend fun loginUser(@Body user: UserInfo): Response<UserInfo>
}