package au.com.carsales.basemodule.viewdata.projectretail.action.showitemdetails

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import java.io.Serializable

class ShowItemDetailsActionVD(
        actionType: String?,
        target: String? = null,
        trackingViewData: TrackingViewData? = null,
        clickTrackingUrls: List<String>? = null,
        val silo: String? = null,
        val id: String? = null
) : Serializable, ActionViewData(
        actionType, target, trackingViewData,
        clickTrackingUrls
)

