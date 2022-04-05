package com.gonzalezcs.covidnewmodule.domain

import com.gonzalezcs.covidnewmodule.data.Repository.CovidDateRepository
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import javax.inject.Inject

class GetCovidByDateUseCase @Inject constructor(private val covidDateRepository: CovidDateRepository){
    suspend fun getCovidByDate(date:String): DataCovidModel?{
        return covidDateRepository.getRepoCovidByDate(date)
    }
}