package au.com.carsales.basemodule.api.data.commons.tracking.eventTrackings

import au.com.carsales.basemodule.api.data.commons.tracking.fabric.FabricData
import com.google.gson.annotations.SerializedName
@Deprecated("use TrackingData")
class EventTrackingItemData {

    @field:SerializedName("fabrics")
    var fabrics: List<FabricData>? = null

    @field:SerializedName("fb")
    var fb: FabricData? = null

}