package au.com.carsales.basemodule.tracking.model.facebook

import java.io.Serializable


data class FbViewData(
        val event: String? = null,
        val attributes: MutableMap<String, String>? = null
) : Serializable {
}