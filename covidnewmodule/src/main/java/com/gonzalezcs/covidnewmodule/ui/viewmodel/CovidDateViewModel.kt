package com.gonzalezcs.covidnewmodule.ui.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import com.gonzalezcs.covidnewmodule.domain.GetCovidByDateUseCase
import com.gonzalezcs.covidnewmodule.ui.utils.StateView
import com.gonzalezcs.covidnewmodule.ui.utils.ValueFormatClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CovidDateViewModel @Inject constructor(private val getCovidByDateUseCase: GetCovidByDateUseCase): ViewModel() {

    val covidStateViewLiveData: MutableLiveData<StateView<DataCovidModel>> = MutableLiveData()

    fun getCovidByDate(date: String){
        viewModelScope.launch (Dispatchers.IO) {
            covidStateViewLiveData.postValue(StateView.loading(View.VISIBLE))
            val result = getCovidByDateUseCase.getCovidByDate(date)
            if(result!=null){
                covidStateViewLiveData.postValue(StateView.success(result))
            }else{
                covidStateViewLiveData.postValue(StateView.error("Error Api"))
            }
        }
    }
}