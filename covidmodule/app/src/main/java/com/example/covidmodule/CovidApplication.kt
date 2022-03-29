package com.example.covidmodule

import android.content.Context
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.example.covidmodule.di.base.AppComponent
import com.example.covidmodule.di.base.CovidApplicationModule
import com.example.covidmodule.di.base.DaggerAppComponent
import com.example.covidmodule.ui.StatisticsSearchViewModel

class CovidApplication : BaseModuleApplication() {

    companion object {
        internal var applicationComponent: AppComponent? = null

        var _statisticsSearchViewModel: StatisticsSearchViewModel? = null
        var statisticsSearchViewModel: StatisticsSearchViewModel?
            get() {
                if (_statisticsSearchViewModel != null) return _statisticsSearchViewModel
                _statisticsSearchViewModel = getSavedSearchViewModelInstance()!!
                return _statisticsSearchViewModel
            }
            set(value) {
                _statisticsSearchViewModel = value!!
            }
    }

    override fun initLifeCycleManager(): BaseModuleLifeCycleManager {
        return BaseModuleLifeCycleManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }
}

private fun getSavedSearchViewModelInstance(): StatisticsSearchViewModel? {
    val context = getAppContext()
    context.let {
        CovidApplication.statisticsSearchViewModel =
            context.appComponent().getStatisticsSearchViewModel()
    }
    return CovidApplication.statisticsSearchViewModel
}

private fun buildDagger(context: Context): AppComponent {

    if (CovidApplication.applicationComponent == null) {
        CovidApplication.applicationComponent = DaggerAppComponent
            .builder()
            .covidApplicationModule(CovidApplicationModule(context as BaseModuleApplication))
            .build()
    }

    return CovidApplication.applicationComponent!!
}

internal fun Context.appComponent(): AppComponent {
    return buildDagger(this)
}

internal fun BaseModuleFragment.appComponent(): AppComponent {
    return buildDagger(this.context!!.applicationContext as BaseModuleApplication)
}