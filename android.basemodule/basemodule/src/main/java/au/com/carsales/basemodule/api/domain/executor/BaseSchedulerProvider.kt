package au.com.carsales.basemodule.api.domain.executor

import kotlinx.coroutines.CoroutineDispatcher

interface BaseSchedulerProvider {

    val computation: CoroutineDispatcher

    val io: CoroutineDispatcher

    val ui: CoroutineDispatcher
}