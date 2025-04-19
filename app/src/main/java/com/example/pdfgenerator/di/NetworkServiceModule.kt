package com.example.pdfgenerator.di

import com.example.pdfgenerator.data.network.BasicAuthInterceptors
import com.example.pdfgenerator.data.network.ProfileService
import com.example.pdfgenerator.data.network.UserCredentials
import com.example.pdfgenerator.data.network.UserCredentialsService
import com.example.pdfgenerator.data.network.usecase.BranchGetNetworkUseCase
import com.example.pdfgenerator.data.network.usecase.CreateUserUseCase
import com.example.pdfgenerator.data.repository.UserCredentialsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    fun getOkHttpClient(userCredentials: UserCredentials): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(BasicAuthInterceptors(userCredentials)).build()
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getProfileNetworkService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    fun getUserCredentialService(retrofit: Retrofit): UserCredentialsService {
        return retrofit.create(UserCredentialsService::class.java)
    }
}

@Module
@InstallIn(ActivityComponent::class)
object ActivityNetworkComponent {
    @Provides
    fun getBranchNetworkUseCase(profileService: ProfileService) =
        BranchGetNetworkUseCase(profileService)

    @Provides
    fun saveUserInfoUseCase(
        userCredentialsService: UserCredentialsService,
        userCredentialsRepo: UserCredentialsRepo
    ) =
        CreateUserUseCase(userCredentialsService, userCredentialsRepo)
}