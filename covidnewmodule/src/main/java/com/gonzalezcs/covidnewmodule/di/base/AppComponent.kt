package com.gonzalezcs.covidnewmodule.di.base

import android.app.Application
import android.content.Context
import com.gonzalezcs.covidnewmodule.di.base.CovidApplicationModule
import com.gonzalezcs.covidnewmodule.di.RepositoryModule
import com.gonzalezcs.covidnewmodule.di.ViewModelFactoryModule
import com.gonzalezcs.covidnewmodule.di.ViewModelModule
import com.gonzalezcs.covidnewmodule.ui.view.CovidDateActivity
import com.gonzalezcs.covidnewmodule.ui.view.CovidFragment
import com.gonzalezcs.covidnewmodule.ui.viewmodel.CovidDateViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CovidApplicationModule::class, RepositoryModule::class,ViewModelFactoryModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(application: Application)
    fun inject(covidFragment: CovidFragment)
    //fun inject(covidDateActivity: CovidDateActivity)

}

/*interface CovidApplicationComponentExternal {
    fun getStatisticsSearchViewModel(): CovidDateViewModel
}*/