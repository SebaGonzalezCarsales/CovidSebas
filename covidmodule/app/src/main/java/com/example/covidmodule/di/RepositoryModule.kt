package com.example.covidmodule.di

import com.example.covidmodule.db.ApiRemoteDataBase
import com.example.covidmodule.db.AuthTokenInterceptor
import com.example.covidmodule.utils.URL_API
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(AuthTokenInterceptor())
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataBase(httpClient: OkHttpClient): ApiRemoteDataBase {
        return Retrofit.Builder()
            .baseUrl(URL_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(ApiRemoteDataBase::class.java)
    }
}