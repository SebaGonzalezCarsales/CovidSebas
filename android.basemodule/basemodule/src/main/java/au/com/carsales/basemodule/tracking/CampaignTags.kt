package au.com.carsales.basemodule.tracking

import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CampaignTags @Inject constructor() {
    var items: MutableList<GaViewData.Items>? = null
}