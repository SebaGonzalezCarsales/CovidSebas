package com.gonzalezcs.covidnewmodule.di.base

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.dependency.module.BaseApplicationModule
import dagger.Module

@Module
internal class CovidApplicationModule(application: BaseModuleApplication) : BaseApplicationModule(application)