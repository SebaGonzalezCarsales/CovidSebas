package com.example.routertest.covidmodule.lifecycle

import android.app.Application
import au.com.carsales.basemodule.router.IModuleConfig
import com.gonzalezcs.covidnewmodule.CovidModuleLifeCycle
import com.gonzalezcs.covidnewmodule.service.CovidImpl
import com.gonzalezcs.covidnewmodule.service.ICovidNewModuleService

open class CovidModuleRouterServiceLifeCycle(application: Application) : CovidModuleLifeCycle(application) {

    override fun onCreate(config: IModuleConfig) {
        super.onCreate(config)
        //config.registerService(CovidModuleReceivedService::class.java, CovidModuleReceivedServiceImpl::class.java)
        config.registerService(ICovidNewModuleService::class.java, CovidImpl::class.java)

    }
}
