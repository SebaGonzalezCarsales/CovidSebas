package au.com.carsales.basemodule.api.data.dataStoreFactory.source

import au.com.carsales.basemodule.api.data.dataStoreFactory.repository.BaseConcreteDataStore
import au.com.carsales.basemodule.api.data.dataStoreFactory.repository.BaseConcreteRemote
import io.reactivex.Completable
import javax.inject.Inject

class BaseConcreteRemoteDataStore @Inject constructor(private val remote: BaseConcreteRemote) : BaseConcreteDataStore {

    override fun call(url: String): Completable {
        return remote.call(url)
    }

}