package com.gonzalezcs.covidnewmodule.data.network

import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CovidDateService @Inject constructor(private val covidDateClient: CovidDateClient ){

    private val HEADER = "96afa298cbmsh913f910f914494cp110c39jsn01a32d68445e"

    suspend fun getCovidData(date:String): DataCovidModel? {
        //thread secundary not interface
       return withContext(Dispatchers.IO){
           return@withContext try {
               val response = covidDateClient.getApi(HEADER,date)
               response.body()?.data
           } catch (e:Throwable){
               null
           }
        }
    }
}