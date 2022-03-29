package com.example.routertest

import android.content.Context
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.example.routertest.RouterApplication.Companion.applicationComponent
import com.example.routertest.dependency.component.DaggerRouterControlPanelServiceApplicationComponent
import com.example.routertest.dependency.component.RouterControlPanelServiceApplicationComponent
import com.example.routertest.dependency.module.RouterControlPanelServiceApplicationModule

class RouterApplication : BaseModuleApplication() {

    companion object {
        internal var applicationComponent: RouterControlPanelServiceApplicationComponent? = null
    }

    override fun initLifeCycleManager(): BaseModuleLifeCycleManager {
        return BaseModuleLifeCycleManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)

    }
}


internal fun Context.appComponent(): RouterControlPanelServiceApplicationComponent {
    return buildDagger(this)
}

internal fun BaseModuleFragment.appComponent(): RouterControlPanelServiceApplicationComponent {
    return buildDagger(this.context!!.applicationContext as BaseModuleApplication)
}

private fun buildDagger(context: Context): RouterControlPanelServiceApplicationComponent {

    if (applicationComponent == null) {
        applicationComponent =  DaggerRouterControlPanelServiceApplicationComponent
            .builder()
            .routerControlPanelServiceApplicationModule(RouterControlPanelServiceApplicationModule(context as BaseModuleApplication))
            .build()
    }

    return applicationComponent!!
}