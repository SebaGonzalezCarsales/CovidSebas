package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import com.google.gson.annotations.SerializedName

class MediaInfoData {

    @field:SerializedName("photoCount")
    var photoCount: Int? = null

    @field:SerializedName("photoIconUrl")
    var photoIconUrl: String? = null

    @field:SerializedName("videoCount")
    var videoCount: Int? = null

    @field:SerializedName("videoIconUrl")
    var videoIconUrl: String? = null

    @field:SerializedName("videoThumbnailPositionList")
    var videoThumbnailPositionList: List<Int>? = null
}
