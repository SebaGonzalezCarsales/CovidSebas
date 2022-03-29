package au.com.carsales.basemodule.api.domain.repository

import io.reactivex.Completable

interface IBaseConcreteDataRepository {

    fun call(url: String): Completable


}