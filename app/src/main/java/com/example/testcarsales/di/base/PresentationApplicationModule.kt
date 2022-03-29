package com.example.testcarsales.di.base

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.dependency.module.BaseApplicationModule
import dagger.Module

@Module
class PresentationApplicationModule(application: BaseModuleApplication) : BaseApplicationModule(application)
