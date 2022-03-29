package au.com.carsales.basemodule.api.data.commons

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import com.google.gson.annotations.SerializedName

/**
 * Created by kaiwu.li on 14/09/2016.
 */
open class Section : TypeData() {

    @field:SerializedName("sectionType")
    var sectionType: String? = null

    @field:SerializedName("isFormSection")
    var isFormSection: Boolean = false

    @SerializedName("tracking")
    var tracking: TrackingData? = null

    @SerializedName("viewTrackingUrl")
    var viewTrackingUrl: String? = null

    @SerializedName("viewTrackingUrls")
    var viewTrackingUrls: List<String>? = null

    @SerializedName("viewabilityTrackingUrl")
    var viewAbilityTrackingUrl: String? = null

    @SerializedName("viewabilityTrackingUrls")
    var viewAbilityTrackingUrls: List<String>? = null

    @SerializedName("backgroundColour")
    var backgroundColour: String? = null

    @SerializedName("separatorType")
    var separatorType: String? = null

    @SerializedName("separatorColour")
    var separatorColour: String? = null

    @SerializedName("paddingType")
    var paddingType: String? = null

}
