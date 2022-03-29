package au.com.carsales.basemodule.util.commonResources

import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData

/**
 * Created by joseignacio on 1/30/18.
 */

open class Resource<out T> constructor(val status: ResourceState, val data: T?, val message: String?, val apiErrorViewData: APIErrorViewData? = null) {

    val isSuccess: Boolean
        get() = status == ResourceState.SUCCESS
    val isSuccessAndNotEmpty: Boolean
        get() = (status == ResourceState.SUCCESS).and(data != null)
    val isSuccessAndEmpty: Boolean
        get() = (status == ResourceState.SUCCESS).and(data == null)


    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceState.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(ResourceState.ERROR, data, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null)
        }

        fun <T> empty(): Resource<T> {
            return Resource(ResourceState.EMPTY, null, null)
        }
    }

}