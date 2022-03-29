package au.com.carsales.basemodule.viewdata.projectretail.action.showsavedsearches

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowSavedSearchActionData
import javax.inject.Inject


class ShowSavedSearchesActionVDMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<ShowSavedSearchesActionViewData?, ShowSavedSearchActionData?> {

    override fun executeMapping(type: ShowSavedSearchActionData?): ShowSavedSearchesActionViewData? {
        return type?.let {
            ShowSavedSearchesActionViewData(
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