package au.com.carsales.basemodule.api

import android.util.Log
import au.com.carsales.basemodule.api.data.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope

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