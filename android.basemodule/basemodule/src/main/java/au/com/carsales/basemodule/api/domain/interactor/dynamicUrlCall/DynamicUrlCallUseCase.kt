package au.com.carsales.basemodule.api.domain.interactor.dynamicUrlCall

import au.com.carsales.basemodule.api.domain.executor.APIPostExecutionThread
import au.com.carsales.basemodule.api.domain.executor.APIThreadExecutor
import au.com.carsales.basemodule.api.domain.interactor.CompletableUseCase
import au.com.carsales.basemodule.api.domain.repository.IBaseConcreteDataRepository
import io.reactivex.Completable
import javax.inject.Inject

open class DynamicUrlCallUseCase @Inject constructor(private val baseConcreteDataRepository: IBaseConcreteDataRepository,
                                                     threadExecutor: APIThreadExecutor,
                                                     postExecutionThread: APIPostExecutionThread) :
        CompletableUseCase<String>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String): Completable {
        val urlString: String = params

        return baseConcreteDataRepository.call(urlString)
    }
}

