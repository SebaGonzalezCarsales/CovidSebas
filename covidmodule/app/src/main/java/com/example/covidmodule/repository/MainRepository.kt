package com.example.covidmodule.repository

import au.com.carsales.basemodule.api.Repository
import au.com.carsales.basemodule.api.data.Result
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.util.commonResources.State
import com.example.covidmodule.db.ApiRemoteDataBase
import com.example.covidmodule.response.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainRepository @Inject constructor(val remote: ApiRemoteDataBase) : Repository {

    suspend fun getStatistics(date: Date): Result<ResultResponse> {
        return request { remote.getStatistics(formatToQuery(date) ) }
    }

    private fun formatToQuery(date: Date): String =
        SimpleDateFormat("yyyy-MM-dd ").format(date)
}