package au.com.carsales.basemodule.viewcomponent.errorView

import java.io.Serializable
import java.net.HttpURLConnection

/**
 * Created by Dan on 31, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
open class APIErrorViewData(var errorTitle: String? = "Error",
                            var errorMessage: String? = null,
                            val errorId: String? = null,
                            var showTryAgain: Boolean = false,
                            var showClose: Boolean = false,
                            var showSetting: Boolean = false,
                            val errorCode: String? = null,
                            val httpStatusCode: String? = HttpURLConnection.HTTP_INTERNAL_ERROR.toString(),
                            var realHttpStatusCode: Int? = HttpURLConnection.HTTP_INTERNAL_ERROR):Serializable {

    open fun getFooterString(): String? {
        return errorId?.let {
            "$realHttpStatusCode/$errorId"
        } ?: "$realHttpStatusCode"

    }
}