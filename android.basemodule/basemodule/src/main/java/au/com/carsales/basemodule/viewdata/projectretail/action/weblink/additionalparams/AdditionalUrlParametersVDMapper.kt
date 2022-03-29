package au.com.carsales.basemodule.viewdata.projectretail.action.weblink.additionalparams

import au.com.carsales.basemodule.api.data.projectretail.action.weblink.AdditionalUrlParameters
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

class AdditionalUrlParametersVDMapper @Inject constructor() :
    Mapper<AdditionalUrlParametersViewData, AdditionalUrlParameters> {
    override fun executeMapping(type: AdditionalUrlParameters?): AdditionalUrlParametersViewData? {
        return type?.let {
            AdditionalUrlParametersViewData(
                email = it.email,
                firstName = it.firstName,
                lastName = it.lastName,
                postcode = it.postcode,
                phone = it.phone,
                memberTrackingId = it.memberTrackingId
            )
        }
    }
}