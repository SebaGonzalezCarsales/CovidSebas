package au.com.carsales.basemodule.api.data.commons.tracking.ga

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GaData(

        @field:SerializedName("eventKey")
        val eventKey: String? = null,

        @field:SerializedName("eventType")
        val eventType: String? = null,

        @field:SerializedName("memberTrackingId")
        val memberTrackingId: String? = null,

        @field:SerializedName("items")
        val items: List<GaItemData?>? = null,

        @field:SerializedName("detailsItems")
        val detailsItems: List<GaItemData?>? = null,

        @field:SerializedName("galleryItems")
        val galleryItems: List<GaItemData?>? = null,

        @field:SerializedName("enquiryFormItems")
        val enquiryFormItems: List<GaItemData?>? = null,

        @field:SerializedName("thankYouItems")
        val thankYouItems: List<GaItemData?>? = null,

        @field:SerializedName("callConnectItems")
        val callConnectItems: List<GaItemData?>? = null,

        @field:SerializedName("callMeBackItems")
        val callMeBackItems: List<GaItemData?>? = null,

        @field:SerializedName("sellerNumberItems")
        val sellerNumberItems: List<GaItemData?>? = null,

        @field:SerializedName("smsVerificationItems")
        val smsVerificationItems: List<GaItemData?>? = null

): Serializable