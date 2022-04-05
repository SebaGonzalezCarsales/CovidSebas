package com.example.routertest.dependency.component

import au.com.carsales.basemodule.dependency.component.BaseApplicationComponent
import com.example.routertest.dependency.module.RouterControlPanelServiceApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RouterControlPanelServiceApplicationModule::class])
interface RouterComponent : BaseApplicationComponent
