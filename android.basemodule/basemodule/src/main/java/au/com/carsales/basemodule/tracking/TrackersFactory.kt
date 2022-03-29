package au.com.carsales.basemodule.tracking

import android.content.Context
import au.com.carsales.basemodule.tracking.bi.BiTracker
import au.com.carsales.basemodule.tracking.fabricAutogate.FabricTracker
import au.com.carsales.basemodule.tracking.facebook.FacebookTracker
import au.com.carsales.basemodule.tracking.gtm.FirebaseTracker
import au.com.carsales.basemodule.tracking.gtm.GATracker
import au.com.carsales.basemodule.tracking.gtm.GTMTracker
import au.com.carsales.basemodule.tracking.krux.KruxTracker
import au.com.carsales.basemodule.tracking.url.UrlTracker
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackersFactory @Inject constructor(membershipSessionInfo: MembershipSessionInfo, campaignTags: CampaignTags) {
    companion object {
        var gaTracker: GATracker? = null
        var fireBaseTracker: FirebaseTracker? = null
        var fabricTracker: FabricTracker? = null
        var facebookTracker: FacebookTracker? = null
        var kruxTracker: KruxTracker? = null
        var biTracker: BiTracker? = null
        var sessionId: String? = null
        var urlTracker: UrlTracker? = null
        var membershipSessionInfo: MembershipSessionInfo? = null
        var campaignTags: CampaignTags? = null

    }


    fun restartSession(uuid: String) {
        sessionId = uuid
    }

    init {
        TrackersFactory.membershipSessionInfo = membershipSessionInfo
        TrackersFactory.campaignTags = campaignTags
    }

    var firebaseAnalyticsInst: FirebaseAnalytics? = null


    fun gaInit(context: Context, containerId: String, defaultContainerResId: Int,
               appInstantId: String, debugMode: Boolean) {
        if (gaTracker == null) {
            val gtmTacker = GTMTracker(context, containerId, defaultContainerResId, debugMode)
            firebaseInit(context)
            gaTracker = GATracker(appInstantId, gtmTacker, fireBaseTracker, campaignTags)
        }
    }

    //can be init seperately, or with Ga
    fun firebaseInit(context: Context, appInstantId: String? = null) {
        if (fireBaseTracker == null) {
            firebaseAnalyticsInst = FirebaseAnalytics.getInstance(context)
            firebaseAnalyticsInst?.setUserProperty("appinstanceid", appInstantId)
            fireBaseTracker = FirebaseTracker(firebaseAnalyticsInst)
        }
    }

    // This used by AG
    fun fabricInit(context: Context) {
        if (fabricTracker == null) {
            fabricTracker = FabricTracker.getInstance(context)
        }
    }


    fun facebookInit(context: Context) {
        if (facebookTracker == null) {
            facebookTracker = FacebookTracker.getInstance(context)
        }
    }


    fun kruxInit(context: Context, kruxConfigId: String, appName: String) {
        if (kruxTracker == null) {
            kruxTracker = KruxTracker.getInstance(context, kruxConfigId, appName)
        }
    }

    fun biInit(context: Context) {
        if (biTracker == null) {
            biTracker = BiTracker.getInstance(context)
        }
    }

    fun urlInit(context: Context) {
        if (urlTracker == null) {
            urlTracker = UrlTracker.getInstance(context)
        }
    }


}