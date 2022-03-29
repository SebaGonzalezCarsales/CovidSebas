package au.com.carsales.basemodule.api.data.commons.tracking.facebook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FbData(
        @field:SerializedName(value = "event", alternate = ["Event"])
        var event: String? = null,
        @field:SerializedName(value = "attributes", alternate = ["Attributes"])
        var attributes: MutableMap<String, String>? = null
) : Serializable