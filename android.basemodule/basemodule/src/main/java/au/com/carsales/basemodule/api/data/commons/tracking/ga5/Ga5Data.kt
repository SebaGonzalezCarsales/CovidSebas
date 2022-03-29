package au.com.carsales.basemodule.api.data.commons.tracking.ga5

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by anibalbastias on 2019-07-30.
 */

class Ga5Data(

        @field:SerializedName("event")
        var event: String? = null,

        @field:SerializedName("attributes")
        var attributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("detailsAttributes")
        var detailsAttributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("galleryAttributes")
        var galleryAttributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("callConnectAttributes")
        var callConnectAttributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("callMeBackAttributes")
        var callMeBackAttributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("sellerNumberAttributes")
        var sellerNumberAttributes: MutableMap<String?, String?>? = null,

        @field:SerializedName("smsVerificationAttributes")
        var smsVerificationAttributes: MutableMap<String?, String?>? = null

) : Serializable