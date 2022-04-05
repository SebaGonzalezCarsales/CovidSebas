package com.gonzalezcs.covidnewmodule

import android.app.Application
import au.com.carsales.basemodule.router.BaseModuleLifeCycle
import au.com.carsales.basemodule.router.IModuleConfig

open class CovidModuleLifeCycle(application: Application) : BaseModuleLifeCycle(application)
{
    override fun onCreate(config: IModuleConfig) {
        //config.registerFragmentRouter("app://helpmechoose/EnterFragment", HelpMeQuestionsFragment::class.java)
        //config.registerRouterActivity("app://helpmechoose/HelpMeChooseActivity", MainActivity::class.java)
    }
}