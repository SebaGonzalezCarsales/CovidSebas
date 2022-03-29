package au.com.carsales.basemodule.api.data.dataStoreFactory

import au.com.carsales.basemodule.api.data.dataStoreFactory.source.BaseConcreteStoreFactory
import au.com.carsales.basemodule.api.domain.repository.IBaseConcreteDataRepository
import io.reactivex.Completable
import javax.inject.Inject

class BaseConcreteDataRepository @Inject constructor(private val baseConcreteStoreFactory: BaseConcreteStoreFactory)
    : IBaseConcreteDataRepository {
    override fun call(url: String): Completable {
        return baseConcreteStoreFactory.retrieveRemoteDataStore().call(url)
    }

}