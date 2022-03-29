package au.com.carsales.basemodule.tracking.gtm

import android.os.Bundle
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import au.com.carsales.basemodule.tracking.model.ga5.Ga5ViewData

import com.google.firebase.analytics.FirebaseAnalytics


/**
 * Created by kaiwu.li on 22/10/2018.
 */

class FirebaseTracker(var firebaseAnalytics: FirebaseAnalytics?) {


    fun track(ga5ViewData: Ga5ViewData?) {
        val eventKey = ga5ViewData?.event
        logTagEvent(eventKey, ga5ViewData?.attributes)
    }

    fun logEvent(eventName: String?, params: Bundle?) {
        if (eventName == null) {
            return
        }
        firebaseAnalytics?.logEvent(eventName, params)
    }

    fun logEvent(eventName: String?, tags: List<GaViewData.Items?>?) {
        if (eventName == null) {
            return
        }
        var bundle: Bundle? = null
        if (tags != null) {
            bundle = Bundle()
            for (tag in tags) {
                bundle.putString(tag?.key, tag?.value)
            }
        }
        logEvent(eventName, bundle)
    }

    fun logTagEvent(eventName: String?, tags: MutableMap<String?, String?>?) {
        if (eventName == null) {
            return
        }
        var bundle: Bundle? = null
        if (tags != null) {
            bundle = Bundle()
            for (tag in tags) {
                bundle.putString(tag.key, tag.value)
            }
        }
        logEvent(eventName, bundle)
    }

}
