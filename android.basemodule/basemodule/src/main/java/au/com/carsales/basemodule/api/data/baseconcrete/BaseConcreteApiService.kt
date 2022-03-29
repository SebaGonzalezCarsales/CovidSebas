package au.com.carsales.basemodule.api.data.baseconcrete

import io.reactivex.Completable
import retrofit2.http.*

interface BaseConcreteApiService {

    @GET
    fun call(@Url url: String): Completable

}