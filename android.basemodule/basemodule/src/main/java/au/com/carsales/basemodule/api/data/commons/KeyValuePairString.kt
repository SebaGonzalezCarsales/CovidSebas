package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class KeyValuePairString(
        @field:SerializedName(value = "key", alternate = ["Key"])
        var key: String? = null,

        @field:SerializedName(value = "value", alternate = ["Value"])
        var value: String? = null
) : Serializable