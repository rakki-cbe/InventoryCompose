package com.example.pdfgenerator.data.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

//TODO need to set this user name and password from use creation
class BasicAuthInterceptors : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val credential = Credentials.basic("admin", "admin1")
        val newRequest = request.newBuilder().header("Authorization", credential).build()
        return chain.proceed(newRequest)
    }
}