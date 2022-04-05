package com.example.testcarsales.di.base

import android.app.Application
import au.com.carsales.basemodule.dependency.component.BaseApplicationComponent
import com.example.routertest.dependency.component.RouterComponent
import com.example.testcarsales.MainActivity
import com.gonzalezcs.covidnewmodule.di.ViewModelModule
//import com.example.testcarsales.ui.StatisticsFragment
import com.gonzalezcs.covidnewmodule.ui.view.CovidDateActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [PresentationApplicationModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class]
)
interface PresentationComponent : BaseApplicationComponent/*, FragmentInjector */{
    fun inject(application: Application)
    fun inject(mainActivity: MainActivity)
    fun inject(covidDateActivity: CovidDateActivity)

}

/*interface FragmentInjector {
    fun inject(statisticsFragment: StatisticsFragment)
}*/