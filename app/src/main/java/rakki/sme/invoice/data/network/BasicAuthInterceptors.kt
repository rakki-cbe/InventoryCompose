package rakki.sme.invoice.data.network

import okhttp3.Interceptor
import okhttp3.Response
import rakki.sme.invoice.extension.filterNull

class BasicAuthInterceptors(val userCredentials: UserCredentials) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //We stored this info on launch of application from db
        val credential = userCredentials.userCredentialsInfo.filterNull("")
        val newRequest = request.newBuilder().header("Authorization", credential).build()
        return chain.proceed(newRequest)

    }
}