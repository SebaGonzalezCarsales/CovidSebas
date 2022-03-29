package com.example.covidmodule.di.base

import android.app.Application
import android.content.Context
import com.example.covidmodule.di.RepositoryModule
import com.example.covidmodule.ui.StatisticsSearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CovidApplicationModule::class, RepositoryModule::class])
interface AppComponent : CovidApplicationComponentExternal {
    fun inject(application: Application)
}

interface CovidApplicationComponentExternal {
    fun getStatisticsSearchViewModel(): StatisticsSearchViewModel
}