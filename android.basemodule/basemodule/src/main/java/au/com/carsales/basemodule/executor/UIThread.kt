package au.com.carsales.basemodule.executor

import au.com.carsales.basemodule.api.domain.executor.APIPostExecutionThread

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIThread @Inject constructor() : APIPostExecutionThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

}