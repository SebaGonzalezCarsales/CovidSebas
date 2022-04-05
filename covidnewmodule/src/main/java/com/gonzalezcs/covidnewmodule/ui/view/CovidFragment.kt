package com.gonzalezcs.covidnewmodule.ui.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.util.commonResources.State
import au.com.carsales.basemodule.util.viewModel.getViewModel
import com.gonzalezcs.covidnewmodule.CovidApplication
import com.gonzalezcs.covidnewmodule.R
import com.gonzalezcs.covidnewmodule.appComponent
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import com.gonzalezcs.covidnewmodule.data.model.FormatCalendarObject
import com.gonzalezcs.covidnewmodule.databinding.FragmentCovidBinding
import com.gonzalezcs.covidnewmodule.ui.utils.AnimationViewClass
import com.gonzalezcs.covidnewmodule.ui.utils.StateView
import com.gonzalezcs.covidnewmodule.ui.utils.ValueFormatClass
import com.gonzalezcs.covidnewmodule.ui.viewmodel.CovidDateViewModel
import java.util.*
import javax.inject.Inject
import androidx.fragment.app.viewModels


class CovidFragment : BaseModuleFragment() {

    // You want Dagger to provide an instance of viewModelFactory from the graph
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val covidDateViewModel: CovidDateViewModel by viewModels{ viewModelFactory }


    //@Inject lateinit var covidDateViewModel: CovidDateViewModel

    private lateinit var binding: FragmentCovidBinding

    private val calendarInstance = ValueFormatClass().getCalendarInstance(null)


    override fun tagName(): String = "CovidFragment"

    override fun isFullScreen(): Boolean = true

    override fun layoutId(): Int = R.layout.fragment_covid

    override fun showLoadingView() {}

    override fun showEmptyView() {}

    override fun showErrorView(message: String?) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCovidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //binding.lifecycleOwner = this
        //binding.viewModel = viewModel

        //setting databinding with activity xml
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
            val datepicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, y, m, d ->
                covidDateViewModel.getCovidByDate(ValueFormatClass().setCalendarFormat(y,m,d))
                calendarInstance.year = y
                calendarInstance.month = m
                calendarInstance.day = d
            }, calendarInstance.year, calendarInstance.month, calendarInstance.day)
            datepicker.show()
        }

        //contains all the observers in the activity
        observersActivity()


    }

    //this function groups all observers in the activity
    private fun observersActivity(){
        covidDateViewModel.covidStateViewLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it){
                is StateView.Error -> {
                    AnimationViewClass().setViewAnimationVisibility(
                        binding.appLoading,
                        View.GONE,
                        0.0f ,
                        400)
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
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
    private fun bindingCovidElements(covidDateModel: DataCovidModel?){
        covidDateModel?.let {
            binding.datacovidmodel = it
        }?: run {
            Toast.makeText(requireContext(),getString(R.string.info_not_available), Toast.LENGTH_LONG)
        }
    }

    //binding formatcalendar to view
    private fun bindingFormatCalendar(formatCalendarObject:FormatCalendarObject?){

        formatCalendarObject?.let {
            binding.formatcalendarobject = it
        }
    }

}