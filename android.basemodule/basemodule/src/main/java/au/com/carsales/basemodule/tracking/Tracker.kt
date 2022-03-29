package au.com.carsales.basemodule.tracking

import android.os.Bundle
import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import au.com.carsales.basemodule.tracking.model.ga5.Ga5ViewData
import au.com.carsales.basemodule.util.deepCopy
import java.util.*

class Tracker : BaseTracker() {
    companion object {
        const val CSN_EVENT_ID: String = "csneventid"
    }

    override fun trackUrls(trackingUrl: String?, trackingUrls: List<String?>?) {
        TrackersFactory.urlTracker?.track(trackingUrl, trackingUrls)
    }

    override fun trackFirebase(eventName: String?, params: Bundle?) {
        TrackersFactory.fireBaseTracker?.logEvent(eventName, params)
    }

    override fun track(trackingData: TrackingViewData?) {

        // Ga4 Tracking
        trackingData?.gaViewData?.apply {

            val runtimeGaViewData: GaViewData? =
                    when (eventType) {
                        "click" -> {
                            //append click+baseContext
                            pageTrackingViewData?.gaViewData?.let {
                                val composedClickGaViewData = deepCopy(it)
                                composedClickGaViewData?.items?.removeAll { item -> item?.key.equals("event") }
                                trackingData.gaViewData?.items?.let { itemList ->
                                    composedClickGaViewData?.items?.apply {
                                        addAll(itemList)
                                        add(GaViewData.Items("eventtype", "click"))
                                        add(GaViewData.Items("eventvalue", "0"))
                                    }
                                }
                                composedClickGaViewData
                            }
                        }

                        "pageView" -> {
                            if (pageTrackingViewData == null) pageTrackingViewData = trackingData
                            pageTrackingViewData?.gaViewData?.let {
                                val runTimeGaDataView = deepCopy(this)
                                runTimeGaDataView
                            }

                        }
                        else -> {
                            pageTrackingViewData?.gaViewData.let {
                                val runTimeGaDataView = deepCopy(this)
                                runTimeGaDataView
                            }
                        }
                    }

            if (runtimeGaViewData?.memberTrackingId != null
                    && runtimeGaViewData.memberTrackingId.equals("<!member_tracking_id!>", true)) {
                runtimeGaViewData.memberTrackingId = TrackersFactory.membershipSessionInfo?.memberTrackingId
            }

            TrackersFactory.gaTracker?.track(runtimeGaViewData)
        }

        // Ga5 Tracking
        trackingData?.ga5ViewData?.apply {

            val runtimeGa5ViewData: Ga5ViewData? =
                    when (event) {
                        "interaction" -> {
                            pageTrackingViewData?.ga5ViewData?.let {
                                //deep copy from page view data
                                val composedClickGa5ViewData = deepCopy(it)
                                //change composed event value to "interaction"
                                composedClickGa5ViewData?.event = event
                                //append click attribute
                                trackingData.ga5ViewData?.attributes?.let { clickAttributes ->
                                    composedClickGa5ViewData?.attributes?.apply {
                                        putAll(clickAttributes)
                                    }
                                }
                                composedClickGa5ViewData
                            }
                        }

                        "screenview" -> {
                            if (pageTrackingViewData == null) pageTrackingViewData = trackingData
                            pageTrackingViewData?.ga5ViewData?.let {
                                val runTimeGa5DataView = deepCopy(this)
                                runTimeGa5DataView
                            }


                        }
                        else -> {
                            pageTrackingViewData?.ga5ViewData.let {
                                val runTimeGa5DataView = deepCopy(this)
                                runTimeGa5DataView
                            }
                        }
                    }

            val memberTrackingIdValue = runtimeGa5ViewData?.attributes?.getOrElse("membertrackingid") { null }
            memberTrackingIdValue?.let {
                if (it.equals("<!member_tracking_id!>", true)) {
                    runtimeGa5ViewData.attributes?.set("membertrackingid", TrackersFactory.membershipSessionInfo?.memberTrackingId)
                }
            }

            val biTrackingUrl1 = runtimeGa5ViewData?.attributes?.getOrElse("bitrackingurl1") { null }
            if (!biTrackingUrl1.isNullOrBlank()) {
                if (biTrackingUrl1.contains("<!member_tracking_id!>", true) != false) {
                    val newBiTrackingUrl =
                            biTrackingUrl1.replace("<!member_tracking_id!>", TrackersFactory.membershipSessionInfo?.memberTrackingId
                                    ?: "", true)
                    runtimeGa5ViewData.attributes?.set("bitrackingurl1", newBiTrackingUrl)
                }
            }

            val biTrackingUrl2 = runtimeGa5ViewData?.attributes?.getOrElse("bitrackingurl2") { null }
            if (!biTrackingUrl2.isNullOrBlank()) {
                if (biTrackingUrl2.contains("<!member_tracking_id!>", true) != false) {
                    val newBiTrackingUrl =
                            biTrackingUrl2.replace("<!member_tracking_id!>", TrackersFactory.membershipSessionInfo?.memberTrackingId
                                    ?: "", true)
                    runtimeGa5ViewData.attributes?.set("bitrackingurl2", newBiTrackingUrl)
                }
            }

            //set a guid to csneventid
            runtimeGa5ViewData?.attributes?.put(CSN_EVENT_ID, UUID.randomUUID().toString())

            TrackersFactory.fireBaseTracker?.track(runtimeGa5ViewData)
        }

        // Facebook Tracking
        trackingData?.fbViewData?.apply {
            TrackersFactory.facebookTracker?.track(this)
        }

        // BI Tracking
        trackingData?.biViewData?.apply {
            val runtimeBiViewData = deepCopy(this)
            val iterator = runtimeBiViewData?.trackingUrls?.listIterator()
            while (iterator?.hasNext() ?: false) {
                val oldvalue = iterator?.next()//
                if (oldvalue?.contains("<!member_tracking_id!>", true) != false) {
                    val newValue =
                            oldvalue?.replace("<!member_tracking_id!>", TrackersFactory.membershipSessionInfo?.memberTrackingId
                                    ?: "", true)
                    iterator?.set(newValue!!)
                }
            }
            TrackersFactory.biTracker?.track(runtimeBiViewData)
        }

        // Krux Tracking
        val runtimeKruxViewDataList =
                trackingData?.kruxViewDataList?.mapNotNull {
                    deepCopy(it)
                }

        runtimeKruxViewDataList?.apply {
            this.forEach { kruxItemVM ->
                kruxItemVM.items?.forEach {
                    if (it.value.equals("<!MEMBER_TRACKING_ID!>") || it.value.equals("<!GUEST_MEMBER_TRACKING_ID!>")) {
                        it.value = TrackersFactory.membershipSessionInfo?.memberTrackingId
                    }
                }
            }

        }.apply { TrackersFactory.kruxTracker?.track(this) }

        // Krux fireEvent
        trackingData?.kruxFireEventViewDataList?.apply {
            TrackersFactory.kruxTracker?.fireEvents(this)
        }
    }
}
