package au.com.carsales.basemodule.viewdata.projectretail.action.weblink.additionalparams

import java.io.Serializable

data class AdditionalUrlParametersViewData(val email: String? = null, val firstName: String? = null,
                                           val lastName: String? = null,
                                           val postcode: String? = null, val phone: String? = null,
                                           val memberTrackingId: String? = null) : Serializable