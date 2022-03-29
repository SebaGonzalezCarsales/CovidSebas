package au.com.carsales.basemodule.api.data.commons

import au.com.carsales.basemodule.api.data.commons.style.StyleData
import au.com.carsales.basemodule.api.data.commons.tracking.eventTrackings.EventTrackingsData
import com.google.gson.annotations.SerializedName

/**
 * Created by joseignacio on 2/13/18.
 */
class ButtonData {

    @field:SerializedName("type")
    var type: String? = null

    @field:SerializedName(value = "action", alternate = ["Action"])
    var action: BaseActionData? = null

    @field:SerializedName(value = "style", alternate = ["Style"])
    var style: StyleData? = null

    @field:SerializedName(value = "title", alternate = ["Text", "Label", "Title"])
    var title: String? = null

    @field:SerializedName("eventTrackings")
    var eventTrackings: EventTrackingsData? = null

}