package com.gonzalezcs.covidnewmodule.di

import androidx.lifecycle.ViewModel
import com.gonzalezcs.covidnewmodule.ui.viewmodel.CovidDateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CovidDateViewModel::class)
    abstract fun bindStatisticsViewModel(viewmodel: CovidDateViewModel): ViewModel
}