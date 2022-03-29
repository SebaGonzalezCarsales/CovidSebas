package au.com.carsales.basemodule.data

import au.com.carsales.basemodule.util.commonResources.Resource
import au.com.carsales.basemodule.util.commonResources.ResourceState
import retrofit2.Response
import java.io.IOException


suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
): T? {
    val result: Resource<T> = safeApiResult(call, errorMessage)
    var data: T? = null
    when (result.status) {
        ResourceState.SUCCESS ->
            data = result.data
        ResourceState.ERROR -> {
            throw IOException(result.message.orEmpty())
        }
        else -> {
        }
    }
    return data

}

suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
): Resource<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) Resource.success(response.body())
        else {
            Resource.error(errorMessage + " " + response.errorBody()!!.string())
        }
    } catch (e: Exception) {
        Resource.error(e.localizedMessage)
    }
}
