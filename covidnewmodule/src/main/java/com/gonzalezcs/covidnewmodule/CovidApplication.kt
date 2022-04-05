package com.gonzalezcs.covidnewmodule

import android.content.Context
import androidx.fragment.app.Fragment
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.gonzalezcs.covidnewmodule.di.base.AppComponent
import com.gonzalezcs.covidnewmodule.di.base.CovidApplicationModule
import com.gonzalezcs.covidnewmodule.di.base.DaggerAppComponent
//import com.gonzalezcs.covidnewmodule.di.base.DaggerAppComponent
import com.gonzalezcs.covidnewmodule.ui.viewmodel.CovidDateViewModel

internal class CovidApplication : BaseModuleApplication() {

    companion object {
        var applicationComponent: AppComponent? = null
    }

    override fun initLifeCycleManager(): BaseModuleLifeCycleManager {
        return BaseModuleLifeCycleManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }
}

fun Fragment.appComponent(): AppComponent {
    return buildDagger(this.requireContext().applicationContext)
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

internal fun BaseModuleApplication.appComponent(): AppComponent {
    return buildDagger(context!!.applicationContext as BaseModuleApplication)
}