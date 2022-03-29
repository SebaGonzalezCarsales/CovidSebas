package com.example.covidmodule.db

import com.example.covidmodule.utils.KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header(KEY.first, KEY.second)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}