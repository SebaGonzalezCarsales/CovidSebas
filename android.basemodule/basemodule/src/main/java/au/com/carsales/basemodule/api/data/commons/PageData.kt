package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class PageData(
        @SerializedName("pageType")
        val pageType: String? = null
) : TypeData(), Serializable