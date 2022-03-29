package com.example.covidmodule.db

import com.example.covidmodule.response.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRemoteDataBase {
    @GET("reports/total")
    suspend fun getStatistics(@Query("date") date: String): ResultResponse

    companion object {}
}