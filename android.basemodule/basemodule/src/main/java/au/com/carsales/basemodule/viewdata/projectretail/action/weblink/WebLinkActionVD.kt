package au.com.carsales.basemodule.viewdata.projectretail.action.weblink

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import au.com.carsales.basemodule.viewdata.projectretail.action.weblink.additionalparams.AdditionalUrlParametersViewData
import java.io.Serializable

class WebLinkActionVD(
        actionType: String? = null,
        target: String? = null,
        trackingViewData: TrackingViewData? = null,
        clickTrackingUrls: List<String>? = null,
        url: String? = null,
        var silo: String? = null,
        var inAppBrowserTitle: String? = null,
        var openInExternalBrowser: Boolean? = false,
        var isPDF: Boolean? = false,
        var additionalUrlParametersViewData: AdditionalUrlParametersViewData? = null
) : Serializable, ActionViewData(actionType, target, trackingViewData, clickTrackingUrls, url)