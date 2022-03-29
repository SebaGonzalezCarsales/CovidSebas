package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.media

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import com.google.gson.annotations.SerializedName

/**
 * Created by joseignacio on 3/13/18.
 */
data class DetailMediaData(
        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("photos")
        val photos: List<String>? = null,

        @field:SerializedName("tracking")
        val trackingData: TrackingData? = null,

        @field:SerializedName("videos")
        var videos: List<MediaVideoData>? = null,

        @field:SerializedName("virtualTours")
        var virtualTours: List<MediaVirtualTourData>? = null
)