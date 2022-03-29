package com.example.covidmodule.service

import au.com.carsales.basemodule.api.data.Result
import com.example.covidmodule.response.ResultResponse
import java.util.*

interface ICovidModuleService {
    suspend fun getStatistics(date: Date) : Result<ResultResponse>
}