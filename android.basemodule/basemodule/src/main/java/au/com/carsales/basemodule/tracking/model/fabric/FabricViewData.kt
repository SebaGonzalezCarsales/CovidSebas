package au.com.carsales.basemodule.tracking.model.fabric

import java.io.Serializable


data class FabricViewData(

        val attributes: MutableMap<String, Any?>? = null,
        val event: String? = null

) : Serializable