package au.com.carsales.basemodule.extension

import android.content.Context
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.api.data.commons.APIErrorData
import au.com.carsales.basemodule.api.data.commons.GlobalSignInAPIException
import au.com.carsales.basemodule.util.orFalse
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorMapper
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.isNotAuthorizationException(): Boolean {
    return this.message?.contains("401").orFalse()
}

/**
 * Gets the API error body response
 * (in case is http exception) and
 * transform it to APIErroViewData class
 */
fun handleAPIErrorResponse(context: Context?, exception: Throwable?): APIErrorViewData? {

    val defaultErrorTitle = context?.getString(R.string.default_error_title)
    val defaultErrorMessage = context?.getString(R.string.default_error_message)


    return when (exception) {

        is HttpException -> {
            // APIErrorViewData object with default title and message
            var apiErrorViewData: APIErrorViewData? = APIErrorViewData(realHttpStatusCode = exception.code(),
                    errorTitle = defaultErrorTitle,
                    errorMessage = defaultErrorMessage)

            try {
                exception.response()?.let { response ->
                    val parsedError = Gson().fromJson(response.errorBody()?.string(), APIErrorData::class.java)

                    apiErrorViewData = APIErrorMapper().executeMapping(parsedError)
                            ?: APIErrorViewData()

                    apiErrorViewData?.let {
                        it.realHttpStatusCode = exception.code()

                        if (it.errorTitle.isNullOrBlank()) {
                            it.errorTitle = defaultErrorTitle
                        }

                        if (it.errorMessage.isNullOrBlank()) {
                            it.errorMessage = defaultErrorMessage
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return apiErrorViewData
        }

        is UnknownHostException -> {
            APIErrorViewData(errorTitle = defaultErrorTitle,
                    errorMessage = defaultErrorMessage)
        }

        is SocketException -> {

            APIErrorViewData(errorTitle = defaultErrorTitle,
                    errorMessage = defaultErrorMessage)
        }

        is SocketTimeoutException -> {
            APIErrorViewData(errorTitle = defaultErrorTitle,
                    errorMessage = defaultErrorMessage)
        }

        is GlobalSignInAPIException -> {
            APIErrorViewData(
                    errorId = "422",
                    errorCode = "422",
                    errorTitle = exception.message,
                    errorMessage = exception.errors?.first()?.message)
        }

        else -> {
            when {
                exception?.isNotAuthorizationException() == true -> {
                    // Not authorization exception APIErrorViewData should notify to caller in onAPIError
                    APIErrorViewData(
                            errorCode = "401",
                            errorTitle = defaultErrorTitle,
                            errorMessage = defaultErrorMessage)
                }

                else -> {
                    //Default flow
                    exception?.printStackTrace()

                    APIErrorViewData(
                            errorTitle = defaultErrorTitle,
                            errorMessage = defaultErrorMessage
                    )
                }
            }
        }
    }
}