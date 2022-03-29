package au.com.carsales.basemodule.api.data.dataStoreFactory.repository

import io.reactivex.Completable

interface BaseConcreteDataStore {

    fun call(url: String): Completable

}