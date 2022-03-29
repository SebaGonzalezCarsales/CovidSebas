package au.com.carsales.basemodule.api.domain.executor

import io.reactivex.Scheduler

interface APIPostExecutionThread {
    val scheduler: Scheduler
}
