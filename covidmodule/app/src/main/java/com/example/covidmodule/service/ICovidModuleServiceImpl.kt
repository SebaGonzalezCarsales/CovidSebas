package com.example.covidmodule.service

import com.example.covidmodule.CovidApplication.Companion.statisticsSearchViewModel
import com.example.covidmodule.response.ResultResponse
import au.com.carsales.basemodule.api.data.Result
import java.util.*

class ICovidModuleServiceImpl : ICovidModuleService {
    override suspend fun getStatistics(date: Date): Result<ResultResponse> =
        statisticsSearchViewModel?.getStatistics(date)!!
}