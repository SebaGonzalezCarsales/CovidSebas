package au.com.carsales.basemodule.extension

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.api.data.Result
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.util.commonResources.State

fun <T> Result<T>?.toState(): State<T> {
    return when (this) {
        is Result.Success -> State.success(data)
        is Result.Error -> State.error(exception.message
                ?: "Unknown Error", handleAPIErrorResponse(context, exception), exception)
        else -> State.errorEmpty()
    }
}

fun <D, VD> Result<D?>.toState(mapper: Mapper<VD, D>): State<VD> {
    return when  {
        this is Result.Success && this.data != null -> State.success(mapper.executeMapping(data))
        this is Result.Error -> State.error(exception.message
                ?: "Unknown Error", handleAPIErrorResponse(context, exception), exception)
        else -> State.errorEmpty()
    }
}