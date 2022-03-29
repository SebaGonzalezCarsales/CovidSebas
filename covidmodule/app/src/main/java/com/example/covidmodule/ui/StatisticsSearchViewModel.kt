package com.example.covidmodule.ui

import androidx.lifecycle.ViewModel
import com.example.covidmodule.repository.MainRepository
import java.util.*
import javax.inject.Inject

class StatisticsSearchViewModel @Inject constructor(val repository: MainRepository) : ViewModel() {
    suspend fun getStatistics(date: Date) = repository.getStatistics(date)
}