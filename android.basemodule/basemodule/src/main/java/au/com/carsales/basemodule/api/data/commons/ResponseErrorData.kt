package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName

data class ResponseErrorData(
        //    {"errorMsg":"This email address is already registered","errorCode":"InvalidInput"}
        @field:SerializedName("errorMsg")
        var errorMsg: String? = "",
        @field:SerializedName("errorCode")
        var errorCode: String? = null
)