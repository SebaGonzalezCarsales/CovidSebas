package au.com.carsales.basemodule.api.data.commons.tracking.ga

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GaItemData(

        @field:SerializedName("value")
        val value: String? = null,

        @field:SerializedName("key")
        val key: String? = null
): Serializable