package au.com.carsales.basemodule.api.utils

import android.util.Log
import kotlinx.coroutines.coroutineScope
import au.com.carsales.basemodule.api.data.Result
import kotlinx.coroutines.Deferred

interface Repository {

    suspend fun <T> requestAwait(
            call: () -> Deferred<T>
    ): Result<T> = coroutineScope {
        try {
            val result = call().await()
            Result.success(result)
        } catch (exception: Exception) {
            Log.e("EXCEPTION", exception.localizedMessage)
            Result.error(exception)
        }
    }

    suspend fun <T> request(
            call: suspend () -> T
    ): Result<T> = coroutineScope {
        try {
            Result.success(call())
        } catch (exception: Exception) {
            Log.e("EXCEPTION", exception.localizedMessage)

            Result.error(exception)
        }
    }
}