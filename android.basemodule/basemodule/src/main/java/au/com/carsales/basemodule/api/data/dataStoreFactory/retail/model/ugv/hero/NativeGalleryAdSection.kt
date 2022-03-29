package au.com.carsales.basemodule.api.data.dataStoreFactory.retail.model.ugv.hero

import au.com.carsales.basemodule.api.data.commons.ButtonData
import au.com.carsales.basemodule.api.data.commons.Section
import com.google.gson.annotations.SerializedName

class NativeGalleryAdSection : Section() {

    @SerializedName("title")
    var title: String? = null

    @SerializedName("titleColour")
    var titleColour: String? = null

    @SerializedName("subtitle")
    var subtitle: String? = null

    @SerializedName("subtitleColour")
    var subtitleColour: String? = null

    @SerializedName("logoText")
    var logoText: String? = null

    @SerializedName("logoTextColour")
    var logoTextColour: String? = null

    @SerializedName("logoUrl")
    var logoUrl: String? = null

    @SerializedName("button")
    var button: ButtonData? = null

}