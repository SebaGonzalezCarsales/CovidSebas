package au.com.carsales.basemodule.api.data.commons.tracking.bi

import au.com.carsales.basemodule.api.data.commons.KeyValuePairStringArray
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BIData(

        @field:SerializedName("items", alternate = ["Items"])
        var items: String? = null,

        @field:SerializedName("tags", alternate = ["Tags"])
        var tags: List<KeyValuePairStringArray>? = null,

        @field:SerializedName("properties", alternate = ["Properties"])
        var properties: String? = null,

        @field:SerializedName("gtsItems", alternate = ["GtsItems"])
        var gtsItems: String? = null,

        @field:SerializedName("gtsProperties", alternate = ["GtsProperties"])
        var gtsProperties: String? = null,

        @field:SerializedName("silo", alternate = ["Silo"])
        var silo: String? = null,

        @field:SerializedName("trackingUrls")
        var trackingUrls: List<String>? = null

): Serializable