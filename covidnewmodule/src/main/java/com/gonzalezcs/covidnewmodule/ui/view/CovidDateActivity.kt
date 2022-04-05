package com.gonzalezcs.covidnewmodule.ui.view

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import au.com.carsales.basemodule.BaseModuleActivity
import au.com.carsales.basemodule.BaseModuleNavigationActivity
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.util.viewModel.getViewModel
import com.gonzalezcs.covidnewmodule.CovidApplication
import com.gonzalezcs.covidnewmodule.ui.utils.AnimationViewClass
import com.gonzalezcs.covidnewmodule.ui.utils.ValueFormatClass
import com.gonzalezcs.covidnewmodule.R
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import com.gonzalezcs.covidnewmodule.data.model.FormatCalendarObject
//import com.gonzalezcs.covidnewmodule.di.getViewModel
import com.gonzalezcs.covidnewmodule.ui.utils.StateView
import com.gonzalezcs.covidnewmodule.ui.viewmodel.CovidDateViewModel
import javax.inject.Inject

class CovidDateActivity : BaseModuleNavigationActivity(){

    override fun layoutContainerId() = R.layout.activity_covid_date
    override fun frameContainer(): FrameLayout = findViewById(R.id.fragmentContainer)

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null)
            pushFragmentRootDetail(CovidFragment())
    }

    override fun savedInstanceOfData(outState: Bundle) {}


    /*@RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)*/

        // Make Dagger instantiate @Inject fields in MainnActivity
        //CovidApplication.applicationComponent?.inject(context!!)
        // covidDateViewModel: CovidDateViewModel by viewModels() { viewModelFactory }
        //covidDateViewModel = getViewModel(viewModelFactory)
        // Now is available viewModel

        //setting databinding with activity xml
        /*binding = DataBindingUtil.setContentView(this, R.layout.activity_covid_date)
        binding.formatcalendarobject = FormatCalendarObject(
            calendarInstance.year,
            calendarInstance.month,
            calendarInstance.day,
            ""
        )

        //this.getUserService()?.getUserToast()?.showToast("Router implementation",this)


        binding.tvFecha.text = calendarInstance.stringDate

        //loading visible until first load (day-1)
        covidDateViewModel.getCovidByDate(ValueFormatClass().setCalendarFormat(
            calendarInstance.year,
            calendarInstance.month,
            calendarInstance.day)
        )

        binding.btnDate.setOnClickListener {
            val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                covidDateViewModel.getCovidByDate(ValueFormatClass().setCalendarFormat(y,m,d))
                calendarInstance.year = y
                calendarInstance.month = m
                calendarInstance.day = d
            }, calendarInstance.year, calendarInstance.month, calendarInstance.day)
            datepicker.show()
        }

        //contains all the observers in the activity
        observersActivity()*/
    //}

    //this function groups all observers in the activity
    /*private fun observersActivity(){
        covidDateViewModel.covidStateViewLiveData.observe(this, androidx.lifecycle.Observer {
            when (it){
                is StateView.Error -> {
                    AnimationViewClass().setViewAnimationVisibility(
                        binding.appLoading,
                        View.GONE,
                        0.0f ,
                        400)
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
                is StateView.Loading -> {
                    AnimationViewClass().setViewAnimationVisibility(
                        binding.appLoading,
                        it.visibility,
                        if(it.visibility==View.GONE) 0.0f else 1.0f,
                        400)
                }
                is StateView.Success -> {
                    AnimationViewClass().setViewAnimationVisibility(
                        binding.appLoading,
                        View.GONE,
                        0.0f ,
                        400)

                    bindingCovidElements(it.data)
                    bindingFormatCalendar(FormatCalendarObject(calendarInstance.year,calendarInstance.month,calendarInstance.day,""))
                }
            }
        })
    }

    //binding coviddatemodel to view
    private fun bindingCovidElements(covidDateModel:DataCovidModel?){
        covidDateModel?.let {
            binding.datacovidmodel = it
        }?: run {
            Toast.makeText(this@CovidDateActivity,getString(R.string.info_not_available), Toast.LENGTH_LONG)
        }
    }

    //binding formatcalendar to view
    private fun bindingFormatCalendar(formatCalendarObject:FormatCalendarObject?){

        formatCalendarObject?.let {
            binding.formatcalendarobject = it
        }
    }*/
}