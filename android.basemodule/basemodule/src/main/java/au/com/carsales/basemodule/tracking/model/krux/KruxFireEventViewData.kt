package au.com.carsales.basemodule.tracking.model.krux

import java.io.Serializable

/**
 * Created by kelly.wang on 2020-01-24.
 */
data class KruxFireEventViewData(
        var eventId: String? = null,
        var eventAttributes: MutableMap<String, String>? = null
):Serializable
