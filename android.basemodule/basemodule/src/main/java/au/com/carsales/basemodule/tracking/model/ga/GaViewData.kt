package au.com.carsales.basemodule.tracking.model.ga

import java.io.Serializable

data class GaViewData(
        var memberTrackingId: String? = null,
        var eventKey: String? = null,
        var eventType: String? = null,
        var items: MutableList<Items?>? = null,//will be modified by frond end depend on which page it is
        val detailsItems: List<Items?>? = null,
        val galleryItems: List<Items?>? = null,
        val enquiryFormItems: List<Items?>? = null,
        val thankYouItems: List<Items?>? = null,
        val callConnectItems: List<Items?>? = null,
        val callMeBackItems: List<Items?>? = null,
        val sellerNumberItems: List<Items?>? = null,
        val smsVerificationItems: List<Items?>? = null
) : Serializable {

    data class Items(
            val key: String? = null,
            var value: String? = null
    ) : Serializable
}