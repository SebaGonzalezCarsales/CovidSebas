package au.com.carsales.basemodule.tracking.model.krux

import au.com.carsales.basemodule.api.data.commons.tracking.krux.KruxFireEvent
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by kelly.wang on 2020-01-24.
 */
class KruxFireEventViewDataMapper @Inject constructor() : Mapper<KruxFireEventViewData, KruxFireEvent> {
    override fun executeMapping(type: KruxFireEvent?): KruxFireEventViewData? {
        return if (type != null) {
            val attributes = mutableMapOf<String, String>()
            type.attributes?.let {
                attributes.putAll(it)
            }
            KruxFireEventViewData(type.eventId, attributes)
        } else null
    }
}