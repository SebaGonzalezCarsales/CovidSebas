package au.com.carsales.basemodule.tracking.gtm

import android.text.TextUtils
import android.util.Log
import au.com.carsales.basemodule.tracking.CampaignTags
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import au.com.carsales.basemodule.tracking.model.ga5.Ga5ViewData


/**
 * send 2 tracking
 * 1. Firebase
 * 2. Google Tag Manager
 */

class GATracker(val appInstantId: String,
                var gtmTracker: GTMTracker?,
                var fireBaseTracker: FirebaseTracker?,
                val campaignTags: CampaignTags?) {

    private val TAG = "GATracker"

    // Track for Ga 5
    fun track(ga5ViewData: Ga5ViewData?) {
        val eventKey = ga5ViewData?.event
        fireBaseTracker?.logTagEvent(eventKey, ga5ViewData?.attributes)
    }

    // Track for Ga 4
    fun track(gaViewData: GaViewData?) {
        val eventKey = gaViewData?.eventKey
        val tags = gaViewData?.items
        if (TextUtils.isEmpty(gtmTracker?.containerId)) return

        if (eventKey != null && tags != null) {
            Log.d(TAG, "eventKey: " + eventKey + "screenname: " + findKeyValue("screenname", tags))
        }

        if (tags != null) {
            val appinstanceid = findKeyValue("appinstanceid", tags)
            if (appinstanceid == null) {
                var instantID: GaViewData.Items = GaViewData.Items("appinstanceid", appInstantId)
                Log.d(TAG, "instantID: ${appInstantId}")
                (tags as ArrayList).add(instantID)
            }

            var memberTrackingID = GaViewData.Items("membertrackingid", gaViewData.memberTrackingId)
            (tags as ArrayList).add(memberTrackingID)

            campaignTags?.items?.let {
                tags.addAll(it);
                campaignTags.items = null
            }

        }

        //Firebase Tracking
        //find event name from tags
        if (eventKey != null && tags != null) {
            val eventName = findKeyValue(eventKey, tags)
            if (eventName != null) {
                fireBaseTracker?.logEvent(eventName, tags)
            }
        }
        //GTM Tracking
        gtmTracker?.pushEvent(tags)
    }


    private fun findKeyValue(key: String, tags: List<GaViewData.Items?>?): String? {

        if (TextUtils.isEmpty(gtmTracker?.containerId)) return null

        tags?.let {
            for (keyValuePairString in it) {
                if (key.equals(keyValuePairString?.key, ignoreCase = true)) {
                    return keyValuePairString?.value
                }
            }

        }
        return null
    }
}
