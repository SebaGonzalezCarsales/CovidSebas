package au.com.carsales.basemodule.viewdata.projectretail.action.myaccount

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData

/**
 * Created by anibalbastias on 2019-06-04.
 */

data class MyAccountActionViewData(
        override var actionType: String? = null,
        override var trackingViewData: TrackingViewData? = null,
        override var url: String? = null,
        var actionName: String? = null,
        var openInExternalBrowser: Boolean? = null,
        var openInPopup: Boolean? = null,
        var product: String? = null,
        var id: String? = null,
        var tabKey: String? = null,
        var conversationId: String? = null
) : ActionViewData()