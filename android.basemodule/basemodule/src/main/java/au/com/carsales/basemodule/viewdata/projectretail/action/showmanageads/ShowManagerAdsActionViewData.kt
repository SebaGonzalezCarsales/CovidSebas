package au.com.carsales.basemodule.viewdata.projectretail.action.showmanageads

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData

class ShowManagerAdsActionViewData(
        actionType: String? = null,
        target: String? = null,
        trackingViewData: TrackingViewData? = null,
        clickTrackingUrls: List<String>? = null,
        fabric: FabricViewData? = null,
        tracking: TrackingViewData? = null,
        advertId: String? = null,
        url: String? = null
) : ActionViewData(actionType, target, trackingViewData, clickTrackingUrls)
