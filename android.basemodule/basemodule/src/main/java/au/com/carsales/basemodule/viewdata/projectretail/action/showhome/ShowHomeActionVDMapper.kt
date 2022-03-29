package au.com.carsales.basemodule.viewdata.projectretail.action.showhome

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowHomeActionData
import javax.inject.Inject


class ShowHomeActionVDMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<ShowHomeActionViewData?, ShowHomeActionData?> {

    override fun executeMapping(type: ShowHomeActionData?): ShowHomeActionViewData? {
        return type?.let {
            ShowHomeActionViewData(
                    actionType = it.actionType,
                    target = it.target,
                    trackingViewData = it.tracking.let {
                        trackingDataViewMapper.executeMapping(it)
                    },
                    clickTrackingUrls = it.clickTrackingUrls,
                    url = it.url)
        }

    }
}