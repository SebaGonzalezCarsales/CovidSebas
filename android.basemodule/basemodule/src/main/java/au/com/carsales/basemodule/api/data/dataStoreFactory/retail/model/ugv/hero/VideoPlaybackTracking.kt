package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import com.google.gson.annotations.SerializedName

class VideoPlaybackTracking {

    @field:SerializedName("videoFirstPlayed25PercentTracking")
    var videoFirstPlayed25PercentTracking: TrackingData? = null

    @field:SerializedName("videoFirstPlayed50PercentTracking")
    var videoFirstPlayed50PercentTracking: TrackingData? = null

    @field:SerializedName("videoFirstPlayed75PercentTracking")
    var videoFirstPlayed75PercentTracking: TrackingData? = null

    @field:SerializedName("videoFirstPlayed100PercentTracking")
    var videoFirstPlayed100PercentTracking: TrackingData? = null

    @field:SerializedName("videoPausedTracking")
    var videoPausedTracking: TrackingData? = null

    @field:SerializedName("videoPlayedTracking")
    var videoPlayedTracking: TrackingData? = null

    @field:SerializedName("videoResumedTracking")
    var videoResumedTracking: TrackingData? = null

}
