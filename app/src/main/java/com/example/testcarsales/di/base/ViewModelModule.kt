package com.example.testcarsales.di.base

import androidx.lifecycle.ViewModel
import com.example.testcarsales.ui.StatisticsFragment
import com.example.testcarsales.ui.StatisticsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindStatisticsViewModel(viewmodel: StatisticsViewModel): ViewModel
}