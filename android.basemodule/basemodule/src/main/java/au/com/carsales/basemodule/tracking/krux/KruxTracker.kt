package au.com.carsales.basemodule.tracking.krux

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import au.com.carsales.basemodule.tracking.model.krux.KruxFireEventViewData
import au.com.carsales.basemodule.tracking.model.krux.KruxViewData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.krux.androidsdk.aggregator.KruxEventAggregator
import com.krux.androidsdk.aggregator.KruxSegments
import java.io.IOException
import java.util.*


/**
 * Created by kaiwu.li on 22/04/2016.
 */
class KruxTracker(context: Context, kruxConfigId: String, var appName: String) {

    internal var krux_enable = true
    var mKruxSegments: String? = null
    var advertisingId: String? = null

    companion object {
        val TAG = "KruxTracker"
        val KRUX_PAGE_ATTR = "pageAttribute"
        val KRUX_COMMON = "common"
        val KRUX_USER = "user"
        val KRUX_DETAILS = "details"
        val KRUX_GALLERY = "gallery"
        val KRUX_ENQUIRY = "enquiry"
        val KRUX_SAVEDITEM = "saveditem"

        val KRUX_KEY_USER_MEMBER = "pp_id"
        val KRUX_KEY_USER_GUEST = "pp_g_id"


        private var instance: KruxTracker? = null
        fun getInstance(context: Context, kruxConfigId: String, appName: String): KruxTracker {
            if (instance == null) {
                instance = KruxTracker(context, kruxConfigId, appName)
            }
            return instance as KruxTracker
        }

        @Deprecated("no need to call this method")
        fun generatePageAttrDataView(kruxListModelData: List<KruxViewData>?, subAction: String?): List<KruxViewData>? {

            var pagekruxDataView = KruxViewData(KRUX_PAGE_ATTR, mutableListOf())
            kruxListModelData?.let { it ->
                it.forEach { kruxDataView ->
                    kruxDataView.location?.let { location ->
                        if (location.equals(KRUX_COMMON)) {
                            kruxDataView.items?.map {
                                (pagekruxDataView.items as MutableList).add(KruxViewData.Item(it.key, it.value))
                            }
                        } else if (subAction != null && location.equals(subAction)) {
                            kruxDataView.items?.map {
                                (pagekruxDataView.items as MutableList).add(KruxViewData.Item(it.key, it.value))
                            }

                        } else {

                        }
                    }
                }
                (it as MutableList).add(pagekruxDataView)
            }
            return kruxListModelData

        }

        /*for detail page*/
        fun getKruxDataViewByPageOrAction(kruxListModelData: MutableList<KruxViewData>?, pageAction: String?): MutableList<KruxViewData>? {

            var pageOrActionKruxViewDataList: MutableList<KruxViewData>? = mutableListOf()


            kruxListModelData?.let { it ->
                it.forEach { kruxDataView ->
                    kruxDataView.location?.let { location ->
                        if (location.equals(KRUX_COMMON)
                                || location.equals(KRUX_USER)
                                || (pageAction != null && location.equals(pageAction))) {
                            pageOrActionKruxViewDataList?.add(kruxDataView)
                        }
                    }
                }
            }
            return pageOrActionKruxViewDataList

        }
    }


    init {
        KruxEventAggregator.initialize(context, kruxConfigId, object : KruxSegments {
            override fun getSegments(segments: String) {
                Log.d(TAG, "Krux formatted segments: $segments")
                mKruxSegments = segments
            }
        }, false)


        //Get Advertising Id in Thread
        object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void): String? {
                var id: String? = null

                try {
                    val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                    if (adInfo != null && !adInfo.isLimitAdTrackingEnabled) {
                        id = adInfo.id
                    }
                } catch (e: GooglePlayServicesNotAvailableException) {
                    // Google Play services is not available entirely.
                    Log.e(TAG, "" + e.message)
                } catch (e: GooglePlayServicesRepairableException) {
                    // Encountered a recoverable error connecting to Google Play services.
                    Log.e(TAG, "" + e.message)
                } catch (e: IOException) {
                    // Unrecoverable error connecting to Google Play services (e.g.,
                    // the old version of the service doesn't support getting AdvertisingId).
                    Log.e(TAG, "" + e.message)
                }

                return id
            }

            override fun onPostExecute(id: String?) {
                id?.let {
                    Log.d(TAG, "ad id: $id")
                    advertisingId = id
                }

            }
        }.execute()
    }


    fun track(kruxListModel: List<KruxViewData>?) {

        if (!krux_enable) return

        val pageAttributes = Bundle()
        val userAttributes = Bundle()

        kruxListModel?.let { it ->
            it.forEach { kruxViewData ->

                if (kruxViewData.location.equals(KRUX_USER, ignoreCase = true)) {
                    kruxViewData.items?.map {
                        userAttributes.putString(it.key, it.value)
                    }

                } else {
                    kruxViewData.items?.map {
                        pageAttributes.putString(it.key, it.value)
                    }

                }

            }

            Log.d(TAG, "Krux:app name = $appName")
            Log.d(TAG, "Krux:" + "pageAttributes = " + pageAttributes.toString())
            Log.d(TAG, "Krux:" + "userAttributes = " + userAttributes.toString())
            try {
                KruxEventAggregator.trackPageView(appName, pageAttributes, userAttributes)
            } catch (e: NullPointerException) {
                krux_enable = false
                Log.e(TAG, "NullPointerException")
            } catch (e: Exception) {
                krux_enable = false
                Log.e(TAG, e.message.orEmpty())
            }
        }
    }

    /**
     * Fire event list
     */
    fun fireEvents(fireEvents: List<KruxFireEventViewData>) {
        fireEvents.forEach {
            fireEvent(it)
        }

    }

    /**
     * Events track specific user interactions.  This data is used as parts of an event funnel,
     * where users follow a sequence of events to a goal.
     * Ref: https://konsole.zendesk.com/hc/en-us/articles/226031268-Android-SDK-Implementation-Guide
     */
    fun fireEvent(fireEvent: KruxFireEventViewData) {
        if (fireEvent.eventId != null) {
            val eventAttributesBundle: Bundle = Bundle()
            fireEvent.eventAttributes?.forEach {
                eventAttributesBundle.putString(it.key, it.value)
            }

            KruxEventAggregator.fireEvent(fireEvent.eventId, eventAttributesBundle)
        }
    }

    fun getKruxTagsForSas(): Map<String, String> {
        val tags = HashMap<String, String>()
        mKruxSegments?.let {
            tags["ksg"] = it
        }
        advertisingId?.let {
            tags["kuid"] = it
        }
        return tags
    }
}
