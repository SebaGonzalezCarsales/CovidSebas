package au.com.carsales.basemodule.viewcomponent.errorView

import au.com.carsales.basemodule.api.data.commons.APIErrorData
import au.com.carsales.basemodule.util.Mapper

/**
 * Created by Dan on 31, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class APIErrorMapper : Mapper<APIErrorViewData, APIErrorData> {
    override fun executeMapping(type: APIErrorData?): APIErrorViewData? {

        return type?.let {
            it.localisedMessage?.let { localMessage ->
                APIErrorViewData(
                        errorTitle = "Error",
                        errorMessage = localMessage,
                        errorId = type.errorId,
                        showTryAgain = type.canRefresh?:false,
                        errorCode = type.errorCode,
                        httpStatusCode = type.httpStatusCode)
            } ?: run {
                APIErrorViewData(
                        errorTitle = type.errorTitle,
                        errorMessage = type.errorMessage,
                        errorId = type.errorId,
                        showTryAgain = type.canRefresh?:false,
                        errorCode = type.errorCode,
                        httpStatusCode = type.httpStatusCode)
            }
        }
    }
}