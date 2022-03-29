package au.com.carsales.basemodule.tracking.model.ga

import au.com.carsales.basemodule.api.data.commons.tracking.ga.GaData
import au.com.carsales.basemodule.api.data.commons.tracking.ga.GaItemData
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by joseignacio on 2/26/18.
 */
open class GaViewDataMapper @Inject constructor() : Mapper<GaViewData, GaData> {

    override fun executeMapping(type: GaData?): GaViewData? {
        return if (type != null) GaViewData(type.memberTrackingId,
                type.eventKey,
                type.eventType,
                type.items?.map { mappingGaItems(it!!) } as MutableList<GaViewData.Items?>?,
                type.detailsItems?.map { mappingGaItems(it!!) },
                type.galleryItems?.map { mappingGaItems(it!!) },
                type.enquiryFormItems?.map { mappingGaItems(it!!) },
                type.thankYouItems?.map { mappingGaItems(it!!) },
                type.callConnectItems?.map { mappingGaItems(it!!) },
                type.callMeBackItems?.map { mappingGaItems(it!!) },
                type.sellerNumberItems?.map { mappingGaItems(it!!) },
                type.smsVerificationItems?.map { mappingGaItems(it!!) })
        else null
    }


    private fun mappingGaItems(it: GaItemData): GaViewData.Items? {
        return GaViewData.Items(it.key, it.value)
    }
}

