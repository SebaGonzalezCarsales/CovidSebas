package au.com.carsales.basemodule.viewdata.projectretail.action.showhome

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData

class ShowHomeActionViewData(
        actionType: String? = null,
        target: String? = null,
        trackingViewData: TrackingViewData? = null,
        clickTrackingUrls: List<String>? = null,
        url: String? = null
) : ActionViewData(actionType, target, trackingViewData, clickTrackingUrls)
