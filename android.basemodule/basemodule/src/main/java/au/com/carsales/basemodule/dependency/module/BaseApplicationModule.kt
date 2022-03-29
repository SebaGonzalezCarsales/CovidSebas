package au.com.carsales.basemodule.dependency.module

import au.com.carsales.basemodule.api.domain.executor.APIPostExecutionThread
import au.com.carsales.basemodule.api.domain.executor.APIThreadExecutor
import au.com.carsales.basemodule.api.domain.executor.BaseSchedulerProvider
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.executor.JobExecutor
import au.com.carsales.basemodule.executor.SchedulerProvider
import au.com.carsales.basemodule.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BaseApplicationModule(private val application: BaseModuleApplication) {

    @Provides
    @Singleton
    fun provideApp(): BaseModuleApplication = application

    @Provides
    @Singleton
    fun provideAPIThreadExecutor(jobExecutor: JobExecutor): APIThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideAPIPostExecutionThread(uiThread: UIThread): APIPostExecutionThread = uiThread

    @Provides
    @Singleton
    fun provideBaseSchedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider = schedulerProvider
}