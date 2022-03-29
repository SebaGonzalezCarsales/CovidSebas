package au.com.carsales.basemodule.tracking.model.krux

import au.com.carsales.basemodule.api.data.commons.KeyValuePairString
import au.com.carsales.basemodule.api.data.commons.tracking.krux.KruxData
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

open class KruxViewDataMapper @Inject constructor() : Mapper<KruxViewData, KruxData> {

    override fun executeMapping(type: KruxData?): KruxViewData? {
        return if (type != null)
            KruxViewData(type.location,
                    type.items?.let { list ->
                        list.map {
                        mappingToKruxViewItems(it)
                        }.toMutableList()
                    })
        else null
    }

    private fun mappingToKruxViewItems(it: KeyValuePairString): KruxViewData.Item {
        return KruxViewData.Item(it.key, it.value)
    }
}