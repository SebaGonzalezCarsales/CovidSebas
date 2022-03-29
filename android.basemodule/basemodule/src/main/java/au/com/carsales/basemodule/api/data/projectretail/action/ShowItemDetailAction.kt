package au.com.carsales.basemodule.api.data.projectretail.action

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName

class ShowItemDetailAction : BaseActionData() {

    @field:SerializedName(value = "id", alternate = ["Id"])
    var id: String? = null
    @field:SerializedName(value = "silo", alternate = ["Silo"])
    var silo: String? = null
    @field:SerializedName(value = "vertical", alternate = ["Vertical"])
    var vertical: String? = null

}