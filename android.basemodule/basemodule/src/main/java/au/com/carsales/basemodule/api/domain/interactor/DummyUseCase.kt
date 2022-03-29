package au.com.carsales.basemodule.api.domain.interactor

import au.com.carsales.basemodule.api.domain.executor.APIPostExecutionThread
import au.com.carsales.basemodule.api.domain.executor.APIThreadExecutor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by joseignacio on 03-10-17.
 */

class DummyUseCase<T> @Inject
constructor(threadExecutor: APIThreadExecutor,
            postExecutionThread: APIPostExecutionThread) : UseCase<T>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(): Observable<T>? {
        return null
    }
}
