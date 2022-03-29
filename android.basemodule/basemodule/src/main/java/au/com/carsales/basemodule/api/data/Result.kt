package au.com.carsales.basemodule.api.data

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()


    companion object {
        fun <T> success(data: T): Result<T> =
                Success(data)

        fun error(exception: Exception) : Result<Nothing> =
                Error(exception)
    }
}