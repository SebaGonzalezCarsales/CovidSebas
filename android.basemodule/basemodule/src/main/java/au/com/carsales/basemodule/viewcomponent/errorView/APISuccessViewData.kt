package au.com.carsales.basemodule.viewcomponent.errorView

import java.io.Serializable
import java.net.HttpURLConnection

/**
 * Created by Dan on 31, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class APISuccessViewData(errorTitle: String? = "Success!",
                         errorMessage: String? = null,
                         errorId: String? = null,
                         showTryAgain: Boolean = false,
                         showClose: Boolean = true,
                         showSetting: Boolean = false,
                         errorCode: String? = null,
                         httpStatusCode: String? = HttpURLConnection.HTTP_OK.toString(),
                         realHttpStatusCode: Int? = HttpURLConnection.HTTP_OK) : APIErrorViewData(errorTitle,
        errorMessage,
        errorId,
        showTryAgain,
        showClose,
        showSetting,
        errorCode,
        httpStatusCode,
        realHttpStatusCode), Serializable {
    override fun getFooterString(): String? {
        return null;
    }
}