package au.com.carsales.basemodule.dependency.baseconcreate.module

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.dependency.module.BaseApplicationModule
import dagger.Module

@Module
internal class BaseConcreteApplicationModule(application: BaseModuleApplication) : BaseApplicationModule(application)