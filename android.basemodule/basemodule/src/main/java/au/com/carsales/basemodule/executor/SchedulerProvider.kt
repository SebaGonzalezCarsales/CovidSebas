package au.com.carsales.basemodule.executor

import au.com.carsales.basemodule.api.domain.executor.BaseSchedulerProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulerProvider @Inject constructor() : BaseSchedulerProvider {

    override val computation: CoroutineDispatcher = Dispatchers.Default

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val ui: CoroutineDispatcher = Dispatchers.Main
}