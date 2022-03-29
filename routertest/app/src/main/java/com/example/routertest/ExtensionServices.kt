package com.example.routertest

import android.content.Context
import au.com.carsales.basemodule.router.Router
import com.example.covidmodule.service.ICovidModuleService
import com.gonzalezcs.covidnewmodule.service.ICovidNewModuleService

var covidModuleService: ICovidModuleService? = null
var covidNewModuleService: ICovidNewModuleService? = null

fun Context.getCovidModuleService(): ICovidModuleService? {
    if (covidModuleService == null)
        covidModuleService = Router.getService(ICovidModuleService::class.java)
    return covidModuleService
}

fun Context.getNewModuleService(): ICovidNewModuleService? {
    if (covidNewModuleService == null)
        covidNewModuleService = Router.getService(ICovidNewModuleService::class.java)
    return covidNewModuleService
}