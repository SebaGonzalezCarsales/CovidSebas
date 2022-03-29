package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 31, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * Used to have an error message
 * to be used in all apps
 */
data class APIErrorData(
        @field:SerializedName("errorTitle")
        val errorTitle: String? = null,
        @field:SerializedName("errorMessage")
        val errorMessage: String? = null,
        @field:SerializedName("localisedMessage")
        val localisedMessage: String? = null,
        @field:SerializedName("errorId")
        val errorId: String? = null,
        @field:SerializedName("canRefresh")
        val canRefresh: Boolean? = null,
        @field:SerializedName("errorCode")
        val errorCode: String? = null,
        @field:SerializedName("httpStatusCode")
        val httpStatusCode: String? = null)
