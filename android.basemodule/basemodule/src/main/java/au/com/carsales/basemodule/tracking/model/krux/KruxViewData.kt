package au.com.carsales.basemodule.tracking.model.krux

import java.io.Serializable

data class KruxViewData(

        val location: String? = null,
        val items: MutableList<Item>? = null

) : Serializable {

    data class Item(

            val key: String? = null,
            var value: String? = null
    ) : Serializable

}
