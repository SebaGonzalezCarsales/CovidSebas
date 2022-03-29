package com.example.testcarsales

import android.app.Application
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.example.routertest.covidmodule.lifecycle.CovidModuleRouterServiceLifeCycle


internal class ModuleLifeCycleManager(application: Application) :
    BaseModuleLifeCycleManager(application) {

    init {
        moduleLifeCycleList.add(CovidModuleRouterServiceLifeCycle(application))
    }
}