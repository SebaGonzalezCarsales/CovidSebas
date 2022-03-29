package au.com.carsales.basemodule.dependency.component

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.executor.JobExecutor
import au.com.carsales.basemodule.executor.SchedulerProvider
import au.com.carsales.basemodule.executor.UIThread

interface BaseApplicationComponent {

    fun inject(application: BaseModuleApplication)
    fun threadExecutor(): JobExecutor
    fun postExecutionThread(): UIThread

    fun schedulerProvider(): SchedulerProvider

}