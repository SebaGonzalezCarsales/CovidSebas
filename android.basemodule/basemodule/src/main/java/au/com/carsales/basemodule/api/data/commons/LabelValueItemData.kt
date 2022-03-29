package au.com.carsales.basemodule.api.data.commons

import com.google.gson.annotations.SerializedName

class LabelValueItemData {

    @field:SerializedName("label")
    var label: String? = null

    @field:SerializedName("value")
    var value: String? = null

}