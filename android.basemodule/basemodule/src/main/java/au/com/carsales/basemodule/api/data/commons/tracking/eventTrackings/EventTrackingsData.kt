package au.com.carsales.basemodule.api.data.commons.tracking.eventTrackings

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import com.google.gson.annotations.SerializedName

class EventTrackingsData() {

    @field:SerializedName("confirmed")
    var confirmed: TrackingData? = null

    @field:SerializedName("cancelled")
    var cancelled: TrackingData? = null

    @field:SerializedName("notCompatible")
    var notCompatible: TrackingData? = null

}