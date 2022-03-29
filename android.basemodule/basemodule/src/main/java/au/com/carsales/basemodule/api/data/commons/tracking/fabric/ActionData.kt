package au.com.carsales.basemodule.api.data.commons.tracking.fabric

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class ActionData(

        @field:SerializedName("target")
        val target: String? = null,

        @field:SerializedName("actionType")
        val actionType: String? = null,

        @field:SerializedName("silo")
        val silo: String? = null,

        @field:SerializedName("predicate")
        val predicate: String? = null,

        @field:SerializedName("url")
        val url: String? = null,

        @field:SerializedName("tracking")
        val trackingData: TrackingData? = null,

        @field:SerializedName("openInExternalBrowser")
        val openInExternalBrowser: Boolean? = null,

        @field:SerializedName("numberForDialling")
        val contactNumberForDialling: String? = null,

        @field:SerializedName("numberForDisplay")
        val contactNumberForDisplay: String? = null
): Serializable