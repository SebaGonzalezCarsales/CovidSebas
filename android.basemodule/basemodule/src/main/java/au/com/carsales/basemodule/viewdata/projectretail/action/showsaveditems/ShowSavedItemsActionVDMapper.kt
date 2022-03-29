package au.com.carsales.basemodule.viewdata.projectretail.action.showsaveditems

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowSavedItemsActionData
import javax.inject.Inject


class ShowSavedItemsActionVDMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<ShowSavedItemsActionViewData?, ShowSavedItemsActionData?> {

    override fun executeMapping(type: ShowSavedItemsActionData?): ShowSavedItemsActionViewData? {
        return type?.let {
            ShowSavedItemsActionViewData(
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