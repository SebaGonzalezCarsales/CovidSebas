package au.com.carsales.basemodule.viewdata.projectretail.action.showlisting

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import java.io.Serializable

class ShowListingActionVD(
    actionType: String? = null,
    target: String? = null,
    trackingViewData: TrackingViewData? = null,
    clickTrackingUrls: List<String>? = null,
    var silo: String? = null,
    val sort: String? = null,
    var predicate: String? = null
) : Serializable, ActionViewData(
    actionType, target, trackingViewData,
    clickTrackingUrls
)