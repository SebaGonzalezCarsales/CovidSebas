package au.com.carsales.basemodule.tracking.model.ga5

import au.com.carsales.basemodule.api.data.commons.tracking.ga5.Ga5Data
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by anibalbastias on 2019-07-30.
 */

class Ga5ViewDataMapper @Inject constructor() : Mapper<Ga5ViewData, Ga5Data> {

    override fun executeMapping(type: Ga5Data?): Ga5ViewData? {
        return type?.let {
            Ga5ViewData(
                    event = it.event,
                    attributes = it.attributes,
                    detailsAttributes = it.detailsAttributes,
                    galleryAttributes = it.galleryAttributes,
                    callConnectAttributes = it.callConnectAttributes,
                    callMeBackAttributes = it.callMeBackAttributes,
                    sellerNumberAttributes = it.sellerNumberAttributes,
                    smsVerificationAttributes = it.smsVerificationAttributes
            )
        }
    }
}