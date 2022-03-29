package au.com.carsales.basemodule.api.data.dataStoreFactory.source

import javax.inject.Inject

class BaseConcreteStoreFactory @Inject constructor(private val baseConcreteRemoteDataStore: BaseConcreteRemoteDataStore) {

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): BaseConcreteRemoteDataStore {
        return baseConcreteRemoteDataStore
    }
}