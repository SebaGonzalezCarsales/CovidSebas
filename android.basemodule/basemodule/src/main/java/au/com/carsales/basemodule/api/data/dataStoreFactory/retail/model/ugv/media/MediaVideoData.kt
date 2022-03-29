package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.media

import au.com.carsales.basemodule.api.data.commons.CSMediaItem
import com.google.gson.annotations.SerializedName

class MediaVideoData : CSMediaItem() {
    @field:SerializedName("thumbImageSmallUrl")
    var thumbImageSmallUrl: String? = null

    @field:SerializedName("thumbImageLargeUrl")
    var thumbImageLargeUrl: String? = null

    @field:SerializedName("directUrl")
    var directUrl: String? = null

}