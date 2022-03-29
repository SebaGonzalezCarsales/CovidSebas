package au.com.carsales.basemodule.api.data.commons.tracking.krux

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by kelly.wang on 2020-01-24.
 */
class KruxFireEvent(
        @SerializedName("eventId")
        var eventId: String? = null,
        @SerializedName("attributes")
        var attributes: MutableMap<String, String>? = null,
        //This url is used for mobi, not for apps
        @SerializedName("url")
        var url: String? = null

):Serializable