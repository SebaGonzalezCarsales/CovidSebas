package au.com.carsales.basemodule.api.data.commons.tracking.krux

import au.com.carsales.basemodule.api.data.commons.KeyValuePairString
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class KruxData(

        @field:SerializedName(value = "location", alternate = ["Location"])
        var location: String? = null,

        @field:SerializedName(value = "items", alternate = ["Items"])
        var items: List<KeyValuePairString>? = null
): Serializable