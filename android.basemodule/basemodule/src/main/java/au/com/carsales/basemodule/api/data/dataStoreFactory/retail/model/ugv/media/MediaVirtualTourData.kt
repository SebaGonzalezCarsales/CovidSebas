package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.media

import au.com.carsales.basemodule.api.data.commons.CSMediaItem
import com.google.gson.annotations.SerializedName

class MediaVirtualTourData : CSMediaItem() {

    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null

    @SerializedName("frameUrls")
    var urls: List<String>? = null

}