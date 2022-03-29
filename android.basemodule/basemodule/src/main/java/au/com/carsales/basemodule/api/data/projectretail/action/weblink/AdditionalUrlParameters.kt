package au.com.carsales.basemodule.api.data.projectretail.action.weblink

import com.google.gson.annotations.SerializedName

class AdditionalUrlParameters {

    @field:SerializedName(value = "firstName", alternate = ["FirstName"])
    var firstName: String? = null
    @SerializedName(value = "lastName")
    var lastName: String? = null
    @field:SerializedName(value = "email", alternate = ["Email"])
    var email: String? = null
    @SerializedName(value = "phone")
    var phone: String? = null
    @field:SerializedName(value = "postcode", alternate = ["Postcode"])
    var postcode: String? = null
    @SerializedName(value = "memberTrackingId")
    var memberTrackingId: String? = null

}