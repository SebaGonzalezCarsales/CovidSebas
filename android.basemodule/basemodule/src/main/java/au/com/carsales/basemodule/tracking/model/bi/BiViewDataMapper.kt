package au.com.carsales.basemodule.tracking.model.bi


import au.com.carsales.basemodule.api.data.commons.tracking.bi.BIData
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

open class BiViewDataMapper @Inject constructor() : Mapper<BiViewData, BIData> {
    override fun executeMapping(type: BIData?): BiViewData? {

        val tagMap = HashMap<String, List<String>>()

        //tag list to tag map
        type?.tags?.forEach {
            it.key?.let { key ->
                tagMap.put(key, it.values ?: emptyList())
            }
        }


        return if (type != null)
            BiViewData(type.trackingUrls?.map { it } as MutableList<String>?, type.items, tagMap, type.properties, type.gtsItems,
                    type.gtsProperties, type.silo)
        else null
    }
}

