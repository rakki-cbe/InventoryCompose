package com.example.pdfgenerator.data.network

import com.example.pdfgenerator.extension.filterNull
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptors(val userCredentials: UserCredentials) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //We stored this info on launch of application from db
        val credential = userCredentials.userCredentialsInfo.filterNull("")
        val newRequest = request.newBuilder().header("Authorization", credential).build()
        return chain.proceed(newRequest)

    }
}