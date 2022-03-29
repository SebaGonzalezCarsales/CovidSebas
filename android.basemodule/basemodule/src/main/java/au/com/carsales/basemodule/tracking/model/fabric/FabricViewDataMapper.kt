package au.com.carsales.basemodule.tracking.model.fabric

import au.com.carsales.basemodule.api.data.commons.tracking.fabric.FabricData
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by igor.roman on 09-02-18.
 */
class FabricViewDataMapper @Inject constructor() : Mapper<FabricViewData, FabricData> {
    override fun executeMapping(type: FabricData?): FabricViewData? {
        return type?.let { FabricViewData(type.attributes.toMutableMap(), type.event) }
    }
}