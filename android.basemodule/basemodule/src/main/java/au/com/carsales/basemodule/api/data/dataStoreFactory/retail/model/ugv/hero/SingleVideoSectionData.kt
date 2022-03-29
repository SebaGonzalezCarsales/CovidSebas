package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.Section
import com.google.gson.annotations.SerializedName

class SingleVideoSectionData : Section() {

    @field:SerializedName("videoId")
    var videoId: String? = null

    @field:SerializedName("playerId")
    var playerId: String? = null

    @field:SerializedName("url")
    var url: String? = null

    @field:SerializedName("encodingUrl")
    var encodingUrl: String? = null

    @field:SerializedName("videoApiUrl")
    var videoApiUrl: String? = null

    @field:SerializedName("encoding")
    var encoding: String? = null

    @field:SerializedName("aspectRatio")
    var aspectRatio: Double? = null

    @field:SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null

    @field:SerializedName("autoPlay")
    var autoPlay: Boolean? = null

    @field:SerializedName("videoPlaybackTracking")
    var videoPlaybackTracking: VideoPlaybackTracking? = null

}