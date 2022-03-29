package au.com.carsales.basemodule.baseactionviewdata

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import java.io.Serializable

open class ActionViewData(
        open var actionType: String? = null,
        open var target: String? = null,
        open var trackingViewData: TrackingViewData? = null,
        open var clickTrackingUrls: List<String>? = null,
        open var url: String? = null
) : Serializable {

    var actionFrom: String? = null

}