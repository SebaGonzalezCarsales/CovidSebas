package au.com.carsales.basemodule.viewcomponent

import androidx.databinding.BaseObservable
import android.graphics.Color
import au.com.carsales.basemodule.api.data.commons.Section
import au.com.carsales.basemodule.tracking.model.TrackingViewData
import java.io.Serializable


open class BaseSectionViewModel : BaseObservable, Serializable {
    var sectionType: String? = null
        private set
    var isFormSection = false
    var isViewTrackingSent = false
    var viewTrackingUrl: String? = null
    var backgroundColour: String? = null
    var separatorType: String? = null
    var separatorColour: String? = null
    var paddingType: String? = null
    var trackingViewData: TrackingViewData? = null
    var backgroundColourInt: Int? = null
    var separatorColourInt: Int? = null


    constructor(section: Section?) {
        if (section != null) {
            this.sectionType = section.sectionType
            this.isFormSection = section.isFormSection
            //this.tracking = section.tracking;
        }
    }

    constructor(viewTrackingUrl: String, sectionType: String) {
        this.sectionType = sectionType
        this.viewTrackingUrl = viewTrackingUrl
    }


    constructor(sectionType: String) {
        this.sectionType = sectionType
    }

    constructor(sectionType: String?, isFormSection: Boolean, backgroundColour: String?, separatorType: String?,
                separatorColour: String?, paddingType: String?, viewTrackingUrl: String?,
                trackingViewData: TrackingViewData?) {
        this.sectionType = sectionType
        this.isFormSection = isFormSection
        this.backgroundColour = backgroundColour
        this.separatorType = separatorType
        this.separatorColour = separatorColour
        this.paddingType = paddingType
        this.viewTrackingUrl = viewTrackingUrl
        this.trackingViewData = trackingViewData
        try {
            if (backgroundColour != null) {
                backgroundColourInt = Color.parseColor("#$backgroundColour")
            }

            if (separatorColour != null) {
                separatorColourInt = Color.parseColor("#$separatorColour")
            }

        } catch (e: Exception) {

        }
    }

    constructor() {
        sectionType = ""
    }


}