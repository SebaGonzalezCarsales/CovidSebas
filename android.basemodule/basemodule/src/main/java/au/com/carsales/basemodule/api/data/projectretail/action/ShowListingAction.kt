package au.com.carsales.basemodule.api.data.projectretail.action

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.`$Gson$Types`.arrayOf


open class ShowListingAction : BaseActionData() {

    @field:SerializedName("silo")
    var silo: String? = null

    @field:SerializedName("predicate")
    var predicate: String? = null

    @field:SerializedName(value = "sortBy", alternate = arrayOf("sort"))
    var sortBy: String? = null


}