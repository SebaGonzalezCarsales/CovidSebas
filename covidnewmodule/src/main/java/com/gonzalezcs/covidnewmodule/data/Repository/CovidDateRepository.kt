package com.gonzalezcs.covidnewmodule.data.Repository

import com.gonzalezcs.covidnewmodule.data.LocalStorage.CovidDataLocalSource
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import com.gonzalezcs.covidnewmodule.data.network.CovidDateService
import javax.inject.Inject

class CovidDateRepository @Inject constructor(private val covidDateRemoteSource: CovidDateService/*, private val covidDataLocalSource: CovidDataLocalSource*/) {

    suspend fun getRepoCovidByDate(date: String):DataCovidModel?{
       /* val selectedObject = covidDataLocalSource.getSelectedDate(date)
        return if(selectedObject != null){
            selectedObject
        }else{
            val dataCovid = covidDateRemoteSource.getCovidData(date)
            if (dataCovid != null) {
                covidDataLocalSource.insertSelectedDate(dataCovid)
            }
            return dataCovid
        }*/
        return covidDateRemoteSource.getCovidData(date)

    }
}