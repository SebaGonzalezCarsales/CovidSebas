package au.com.carsales.basemodule.util.commonResources

import androidx.lifecycle.MutableLiveData

/**
 * Created by joseignacio on 1/30/18.
 */

enum class ResourceState {
    SIGN_OUT,
    DEFAULT,
    LOADING,
    LOADING_REFRESH,
    SUCCESS,
    PAGING_SUCCESS,
    ERROR,
    PAGING_ERROR,
    REFRESH_TOKEN,
    RECALL,
    EMPTY
}

fun <T> MutableLiveData<Resource<T>>.postRefreshTokenData(messageError: String?) {
    postValue(Resource(ResourceState.SIGN_OUT, null, messageError))
}