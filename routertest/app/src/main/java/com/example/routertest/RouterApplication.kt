package com.example.routertest

import android.content.Context
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.example.routertest.RouterApplication.Companion.applicationComponent
import com.example.routertest.dependency.component.DaggerRouterComponent
import com.example.routertest.dependency.component.RouterComponent
import com.example.routertest.dependency.module.RouterControlPanelServiceApplicationModule

class RouterApplication : BaseModuleApplication() {

    companion object {
        internal var applicationComponent: RouterComponent? = null
    }

    override fun initLifeCycleManager(): BaseModuleLifeCycleManager {
        return BaseModuleLifeCycleManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)

    }
}


internal fun Context.appComponent(): RouterComponent {
    return buildDagger(this)
}

internal fun BaseModuleFragment.appComponent(): RouterComponent {
    return buildDagger(this.requireContext().applicationContext as BaseModuleApplication)
}

private fun buildDagger(context: Context): RouterComponent {

    if (applicationComponent == null) {
        applicationComponent =  DaggerRouterComponent
            .builder()
            .routerControlPanelServiceApplicationModule(RouterControlPanelServiceApplicationModule(context as BaseModuleApplication))
            .build()
    }

    return applicationComponent!!
}