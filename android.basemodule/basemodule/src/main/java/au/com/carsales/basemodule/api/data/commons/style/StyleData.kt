package au.com.carsales.basemodule.api.data.commons.style

import com.google.gson.annotations.SerializedName

class StyleData {

    @field:SerializedName(value = "backgroundColour", alternate = ["BackgroundColour"])
    var backgroundColour: String? = null

    @field:SerializedName(value = "textColour", alternate = ["TextColour"])
    var textColour: String? = null

    @field:SerializedName(value = "borderColour", alternate = ["BorderColour"])
    var borderColour: String? = null

    @field:SerializedName("styleType")
    var styleType: String? = null

}