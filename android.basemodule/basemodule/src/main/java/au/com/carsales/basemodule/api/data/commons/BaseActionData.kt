package au.com.carsales.basemodule.api.data.commons

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import au.com.carsales.basemodule.api.data.commons.tracking.fabric.FabricData
import com.google.gson.annotations.SerializedName

open class BaseActionData : TypeData() {

    @field:SerializedName(value = "actionType", alternate = ["ActionType"])
    var actionType: String? = null

    @field:SerializedName(value = "clickTrackingUrls", alternate = ["ClickTrackingUrls"])
    var clickTrackingUrls: List<String>? = null

    @field:SerializedName(value = "target", alternate = ["Target"])
    var target: String? = null

    @field:SerializedName("tracking")
    var tracking: TrackingData? = null

    @SerializedName("url")
    var url: String? = null
}