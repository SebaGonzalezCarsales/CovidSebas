package au.com.carsales.basemodule.api.data.projectretail.action

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName

class ShowEditAdActionData: BaseActionData() {

    @field:SerializedName("advertId")
    val advertId: String? = null
}