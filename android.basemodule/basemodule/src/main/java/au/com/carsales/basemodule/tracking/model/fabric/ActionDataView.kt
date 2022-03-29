package au.com.carsales.basemodule.tracking.model.fabric

import au.com.carsales.basemodule.tracking.model.TrackingViewData
import java.io.Serializable

@Deprecated(message = "Don't use this, attributes are now in Fabric Data View")
data class ActionDataView(
        val target: String? = null,
        val actionType: String? = null,
        val silo: String? = null,
        val predicate: String? = null,
        val url: String? = null,
        val openInExternalBrowser: Boolean? = false,
        val trackingViewData: TrackingViewData? = null,
        val contactNumberForDialling: String? = null,
        val contactNumberForDisplay: String? = null
) : Serializable