package com.example.testcarsales.repository

/*import au.com.carsales.basemodule.api.data.Result
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.util.commonResources.State
import com.example.covidmodule.response.SummaryViewData
import com.example.routertest.getCovidModuleService
import com.example.routertest.getNewModuleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class MainRepository @Inject constructor() {
    fun getStatistics(date : Date): Flow<State<SummaryViewData>> {
        return flow {
            emit(State.loading())
            when (val responseStatistics = context?.getCovidModuleService()?.getStatistics(date)) {
                is Result.Success -> emit(State.success(responseStatistics.data.data))
                is Result.Error -> emit(State.error<SummaryViewData>(errorMessage = "Error en datos"))
            }
        }.flowOn(Dispatchers.IO)
    }
}*/