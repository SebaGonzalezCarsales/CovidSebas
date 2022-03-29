package au.com.carsales.basemodule.api.data.commons.tracking.fabric

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class FabricData(
        @SerializedName(value = "attributes", alternate = ["Attributes"])
        var attributes: Map<String, Any?> = HashMap(),

        @field:SerializedName("event")
        val event: String? = null
) : Serializable