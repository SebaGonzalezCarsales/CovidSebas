package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.Section
import com.google.gson.annotations.SerializedName

class DetailYoutubeSectionData : Section() {

    @field:SerializedName("content")
    var content: String? = null

    @field:SerializedName("id")
    var id: String? = null

}