package au.com.carsales.basemodule.tracking.model.facebook

import au.com.carsales.basemodule.api.data.commons.tracking.facebook.FbData
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

open class FbViewDataMapper @Inject constructor() : Mapper<FbViewData, FbData> {

    override fun executeMapping(type: FbData?): FbViewData? {
        return type?.let {
            FbViewData(type.event,
                    type.attributes)
        }
    }
}