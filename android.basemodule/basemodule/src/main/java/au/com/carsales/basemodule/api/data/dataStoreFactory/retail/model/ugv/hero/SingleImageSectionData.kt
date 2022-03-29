package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.Section
import com.google.gson.annotations.SerializedName

class SingleImageSectionData : Section() {
    @field:SerializedName("url")
    var url: String? = null

    @field:SerializedName("aspectRatio")
    var aspectRatio: Double? = null

    @field:SerializedName("needsCropping")
    var needsCropping: Boolean? = null

}