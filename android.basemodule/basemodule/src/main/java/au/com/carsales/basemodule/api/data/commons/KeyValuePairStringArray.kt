package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName

class KeyValuePairStringArray(

        @field:SerializedName(value = "key", alternate = ["Key"])
        var key: String? = null,

        @field:SerializedName(value = "values", alternate = ["Values"])
        var values: List<String>? = null

)