package au.com.carsales.basemodule.util.commonResources

import android.content.Context
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData

sealed class State<T> {

    class Loading<T> : State<T>()
    class LoadingRefresh<T> : State<T>()
    class Default<T> : State<T>()
    data class Error<T>(val errorMessage: String? = context?.resources?.getString(R.string.default_error_message), val errorViewData: APIErrorViewData? = null, val error: Throwable? = null) : State<T>()
    data class Success<T>(var data: T) : State<T>()
    data class PagingSuccess<T>(var data: T) : State<T>()
    data class SignOut<T>(var message: String?) : State<T>()
    data class PagingError<T>(var message: String?) : State<T>()
    data class UpdatePosition<T>(var positionUpdate: Int) : State<T>()
    data class ListItemUpdated<T>(var data: T, var itemPosition: Int = 0) : State<T>()

    data class ErrorEmpty<T>(val errorMessage: String? = null, val errorMessageResId: Int? = null) : State<T>() {
        fun getMessage(context: Context): String {
            return when {
                errorMessageResId != null -> context.getString(errorMessageResId)
                !errorMessage.isNullOrEmpty() -> errorMessage
                else -> {
                    String.empty()
                }
            }
        }
    }


    companion object {
        fun <T> loading(): State<T> =
                Loading()

        fun <T> loadingRefresh(): State<T> = LoadingRefresh()

        fun <T> error(errorMessage: String?, errorViewData: APIErrorViewData? = null, error: Throwable? = null): State<T> =
                Error(errorMessage, errorViewData, error)

        fun <T> success(data: T): State<T> =
                Success(data)

        fun <T> signOut(message: String?): State<T> = SignOut(message)

        fun <T> default(): State<T> = Default()

        fun <T> pagingError(message: String?): State<T> = PagingError(message)

        fun <T> listItemUpdated(data: T, itemPosition: Int): State<T> =
                ListItemUpdated(data, itemPosition)

        fun <T> pagingSuccess(data: T): State<T> =
                PagingSuccess(data)

        fun <T> updatePosition(positionUpdate: Int): State<T> =
                UpdatePosition(positionUpdate)

        fun <T> errorEmpty(errorMessage: String? = null, errorMessageResId: Int? = null): State<T> =
                ErrorEmpty(errorMessage, errorMessageResId)
    }
}