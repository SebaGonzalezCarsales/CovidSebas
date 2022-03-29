package au.com.carsales.basemodule.api.data.baseconcrete.repository

import au.com.carsales.basemodule.api.data.baseconcrete.BaseConcreteApiService
import au.com.carsales.basemodule.api.data.dataStoreFactory.repository.BaseConcreteRemote
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BaseConcreteImpl @Inject constructor(private val baseConcreteApiService: BaseConcreteApiService)
    : BaseConcreteRemote {
    override fun call(url: String): Completable {
        return baseConcreteApiService.call(url)
    }
}