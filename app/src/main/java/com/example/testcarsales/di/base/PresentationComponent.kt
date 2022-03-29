package com.example.testcarsales.di.base

import android.app.Application
import au.com.carsales.basemodule.dependency.component.BaseApplicationComponent
import com.example.testcarsales.MainActivity
import com.example.testcarsales.ui.StatisticsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [PresentationApplicationModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class]
)
interface PresentationComponent : BaseApplicationComponent, FragmentInjector {
    fun inject(application: Application)
    fun inject(mainActivity: MainActivity)
}

interface FragmentInjector {
    fun inject(statisticsFragment: StatisticsFragment)
}