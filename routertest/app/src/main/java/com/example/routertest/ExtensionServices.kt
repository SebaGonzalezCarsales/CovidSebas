package com.example.routertest

import android.content.Context
import au.com.carsales.basemodule.router.Router
import com.gonzalezcs.covidnewmodule.service.ICovidNewModuleService

var covidNewModuleService: ICovidNewModuleService? = null

fun Context.getNewModuleService(): ICovidNewModuleService? {
    if (covidNewModuleService == null)
        covidNewModuleService = Router.getService(ICovidNewModuleService::class.java)
    return covidNewModuleService
}