package com.example.routertest.dependency.module

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.dependency.module.BaseApplicationModule
import dagger.Module

@Module
class RouterControlPanelServiceApplicationModule(application: BaseModuleApplication) : BaseApplicationModule(application)