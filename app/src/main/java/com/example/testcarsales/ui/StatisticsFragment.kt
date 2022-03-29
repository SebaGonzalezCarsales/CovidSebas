package com.example.testcarsales.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import au.com.carsales.basemodule.BaseModuleFragment
import com.example.testcarsales.R
import com.example.testcarsales.appComponent
import com.example.testcarsales.databinding.StatisticsFragmentBinding
import com.example.testcarsales.utils.LoadingProgress
import java.util.*
import javax.inject.Inject
import au.com.carsales.basemodule.util.commonResources.State
import au.com.carsales.basemodule.context


class StatisticsFragment : BaseModuleFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: StatisticsViewModel by viewModels() { viewModelFactory }

    private lateinit var loadingProgress: LoadingProgress

    private lateinit var binding: StatisticsFragmentBinding

    override fun tagName() = "StatisticsFragment"

    override fun isFullScreen() = true

    override fun layoutId() = R.layout.statistics_fragment

    override fun showLoadingView() {}

    override fun showEmptyView() {}

    override fun showErrorView(message: String?) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingProgress = LoadingProgress(requireContext())

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        viewModel.setDate(yesterday.time)

        binding.btnDate.setOnClickListener {
            val dateSelected = viewModel.getDateSelected()
            generateDatePickerDialog(dateSelected)
        }

        viewModel.loadingEvent.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> loadingProgress.show(true)
                is State.Success -> {
                    loadingProgress.show(false)
                    message(R.string.statistics_text_success)
                }
                is State.Error -> {
                    loadingProgress.show(false)
                    message(R.string.statistics_text_not_found)
                }
            }
        }
    }

    private fun generateDatePickerDialog(calendarSelected: Calendar) {
        val dialog =
            DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(year, monthOfYear, dayOfMonth)
                    viewModel.setDate(calendar.time)
                },
                calendarSelected.get(Calendar.YEAR),
                calendarSelected.get(Calendar.MONTH),
                calendarSelected.get(Calendar.DAY_OF_MONTH)
            )

        val minDate = Calendar.getInstance()
        minDate.set(2020, 0, 1)

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DAY_OF_MONTH, -1)

        dialog.datePicker.minDate = minDate.timeInMillis
        dialog.datePicker.maxDate = maxDate.timeInMillis
        dialog.show()
    }

    private fun message(idText: Int) {
        val text = resources.getString(idText)
        Toast.makeText(
            requireContext(), text,
            Toast.LENGTH_SHORT
        ).show()
    }
}