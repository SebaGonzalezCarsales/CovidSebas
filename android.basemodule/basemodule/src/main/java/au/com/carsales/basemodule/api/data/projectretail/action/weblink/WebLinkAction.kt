package au.com.carsales.basemodule.api.data.projectretail.action.weblink

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName

class WebLinkAction : BaseActionData() {

    @field:SerializedName(value = "id", alternate = ["Id"])
    var id: String? = null

    @field:SerializedName(value = "additionalUrlParameters", alternate = ["AdditionalUrlParameters"])
    var additionalUrlParameters: AdditionalUrlParameters? = null

    @SerializedName(value = "inAppBrowserTitle", alternate = ["InAppBrowserTitle"])
    var inAppBrowserTitle: String? = null

    @SerializedName(value = "isPDF", alternate = ["IsPDF"])
    var isPDF: Boolean? = null

    @SerializedName(value = "openInExternalBrowser", alternate = ["OpenInExternalBrowser"])
    var openInExternalBrowser: Boolean? = null

    @field:SerializedName("silo")
    var silo: String? = null

    @SerializedName("noFollow")
    var noFollow: Boolean? = null
}