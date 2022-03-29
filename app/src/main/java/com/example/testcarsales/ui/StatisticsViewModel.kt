package com.example.testcarsales.ui

import androidx.lifecycle.*
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.getAppContext
import com.example.covidmodule.response.ResultResponse
import com.example.covidmodule.response.SummaryViewData
import com.example.testcarsales.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import au.com.carsales.basemodule.util.commonResources.State
import com.example.routertest.getNewModuleService

class StatisticsViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
    private val _statistics: MutableLiveData<SummaryViewData> = MutableLiveData()
    val statistics: LiveData<SummaryViewData> = _statistics

    private val _statisticsDate: MutableLiveData<Date> = MutableLiveData()
    val statisticsDate: LiveData<Date> = _statisticsDate

    private val _showErrorMessage: MutableLiveData<Boolean> = MutableLiveData()
    val showErrorMessage: LiveData<Boolean> = _showErrorMessage

    private val _loadingEvent: MutableLiveData<State<SummaryViewData>> = MutableLiveData()
    val loadingEvent: LiveData<State<SummaryViewData>> = _loadingEvent

    fun setDate(date: Date) {
        _showErrorMessage.value = false
        _statisticsDate.value = date
        //context?.getNewModuleService()?.getUserToast()?.showToast("Sebas Toast", getAppContext())

        viewModelScope.launch(Dispatchers.IO) {
            repository.getStatistics(date).collect {
                when (it) {
                    is State.Success -> _statistics.postValue(it.data)
                    is State.Error -> _showErrorMessage.postValue(true)
                }
                _loadingEvent.postValue(it)
            }
        }
    }

    fun getDateSelected(): Calendar {
        return if (statisticsDate.value == null)
            Calendar.getInstance()
        else {
            val calendar = Calendar.getInstance()
            calendar.time = statisticsDate.value!!
            calendar
        }
    }
}