package au.com.carsales.basemodule.viewdata.projectretail.section

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import java.io.Serializable

open class SectionViewData(
        open var sectionType: String? = null,
        open var isFormSection: Boolean = false,
        open var trackingViewData: TrackingViewData? = null,
        open var backgroundColour: String? = null,
        open var separatorType: String? = null,
        open var separatorColour: String? = null,
        open var paddingType: String? = null,
        open var viewTrackingUrl: String? = null,
        open var viewTrackingUrls: List<String>? = null,
        open var viewAbilityTrackingUrl: String? = null,
        open var viewAbilityTrackingUrls: List<String>? = null
) : Serializable {

    enum class SeparatorType(val value: String) {
        NONE("none"),
        PADDED("padded")
    }

    enum class PaddingType(val value: String) {
        SMALL("small")
    }
}