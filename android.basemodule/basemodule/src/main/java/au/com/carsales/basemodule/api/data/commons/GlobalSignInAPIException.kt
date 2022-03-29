package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName

data class GlobalSignInAPIException(
        @field:SerializedName("message")
        var errorMessage: String? = null,
        var errorCode: String? = null,
        var errors: List<GlobalSignInAPIErrorItem>? = null
) : Exception(errorMessage)