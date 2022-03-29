package au.com.carsales.basemodule.tracking

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.carsales.basemodule.tracking.gtm.GATracker
import au.com.carsales.basemodule.tracking.krux.KruxTracker
import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import au.com.carsales.basemodule.tracking.model.krux.KruxViewData
import au.com.carsales.basemodule.util.mock
import com.nhaarman.mockitokotlin2.*
import io.github.benas.randombeans.api.EnhancedRandom.random
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TrackerTest {

//    @Rule
//    @JvmField
//    val rule = InstantTaskExecutorRule()
//
//    lateinit var trackerFactory: TrackersFactory
//    var pageTrackingViewData: TrackingViewData? = null
//    private var baseTracker: Tracker = Tracker()
//
//    //GA
//    var gaTracker: GATracker = mock<GATracker>()
//
//    //Krux
//    var kruxTracker: KruxTracker = mock<KruxTracker>()
//
//
//    @Before
//    fun setup() {
//        pageTrackingViewData = random(TrackingViewData::class.java)!!
//        var sessionInfo = MembershipSessionInfo()
//        sessionInfo.memberTrackingId = "8989_8989_ioio_ioio"
//        trackerFactory = TrackersFactory(sessionInfo)
//        baseTracker.pageTrackingViewData = pageTrackingViewData
//
//        TrackersFactory.gaTracker = gaTracker
//        pageTrackingViewData?.gaViewData?.memberTrackingId = "<!MEMBER_TRACKING_ID!>"
//        doNothing().whenever(TrackersFactory.gaTracker)?.track(baseTracker.pageTrackingViewData?.gaViewData)
//
//
//        TrackersFactory.kruxTracker = kruxTracker
//
//    }
//
//
//    @Test
//    fun populatePlaceHolderFor_KruxTest() {
//        //setup data
//        val itemListuser = mutableListOf<KruxViewData.Item>()
//        itemListuser.add(KruxViewData.Item("pp_id", "<!MEMBER_TRACKING_ID!>"))
//        var kruxDataView = KruxViewData(
//                "user",
//                itemListuser
//        )
//
//        kruxDataView.items?.add(KruxViewData.Item("pp_g_id", "<!GUEST_MEMBER_TRACKING_ID!>"))
//
//        pageTrackingViewData?.kruxViewDataList?.apply { add(kruxDataView) }
//        //set up mocks
//        doNothing().whenever(TrackersFactory.kruxTracker)?.track(baseTracker.pageTrackingViewData?.kruxViewDataList)
//
//
//
//        baseTracker.track(baseTracker.pageTrackingViewData)
//
//
//        argumentCaptor<MutableList<KruxViewData>>().apply {
//            verify(TrackersFactory.kruxTracker, times(1))?.track(capture())
//
//            var items =
//                    firstValue.find { it.location.equals("user") }?.items
//            var user = items?.find { it.key.equals("pp_id") && it.value.equals("8989_8989_ioio_ioio") }
//            var guest = items?.find { it.key.equals("pp_g_id") && it.value.equals("8989_8989_ioio_ioio") }
//            assert(user!=null)
//            assert(guest!=null)
//
//        }
//
//    }
//
//
//    @Test
//    fun populatePlaceHolderFor_GaTest_PageView_then_clickEvent() {
//
//
//        pageTrackingViewData?.gaViewData?.eventType = "pageView"
//        baseTracker.track(baseTracker.pageTrackingViewData)
//
//        pageTrackingViewData?.gaViewData?.eventType = "click"
//        baseTracker.track(baseTracker.pageTrackingViewData)
//
//
//        argumentCaptor<GaViewData>().apply {
//            verify(TrackersFactory.gaTracker, times(2))?.track(capture())
//
//            assert(firstValue.memberTrackingId == "8989_8989_ioio_ioio")
//            assert(secondValue.memberTrackingId == "8989_8989_ioio_ioio")
//        }
//
//    }


}