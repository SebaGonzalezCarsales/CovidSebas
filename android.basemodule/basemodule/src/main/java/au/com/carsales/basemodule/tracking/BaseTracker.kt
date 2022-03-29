package au.com.carsales.basemodule.tracking

import android.os.Bundle
import au.com.carsales.basemodule.tracking.model.TrackingViewData

abstract class BaseTracker {
    /*
    pageTrackingViewData: This data is the base data for click tracking data, which need append to the
    base one when click event happens
    * */

    var pageTrackingViewData: TrackingViewData? = null

    abstract fun track(trackingData: TrackingViewData?)

    abstract fun trackUrls(trackingUrl: String? = null, trackingUrls: List<String?>? = null)

    abstract fun trackFirebase(eventName: String?, params: Bundle?)


}