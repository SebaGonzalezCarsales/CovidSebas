package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.Section
import com.google.gson.annotations.SerializedName

class HeroSectionData : Section() {

    @field:SerializedName("mediaInfo")
    var mediaInfo: MediaInfoData? = null

    @field:SerializedName("items")
    var items: List<Section>? = null

}