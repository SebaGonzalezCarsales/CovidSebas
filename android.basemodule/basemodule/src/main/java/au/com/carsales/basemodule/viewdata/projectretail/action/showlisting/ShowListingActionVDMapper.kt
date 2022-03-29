package au.com.carsales.basemodule.viewdata.projectretail.action.showlisting

import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.util.Mapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowListingAction
import javax.inject.Inject

class ShowListingActionVDMapper @Inject constructor(
        private val trackingViewDataMapper: TrackingViewDataMapper
) : Mapper<ShowListingActionVD, ShowListingAction?> {
    override fun executeMapping(type: ShowListingAction?): ShowListingActionVD? {
        return type?.let {
            ShowListingActionVD(
                    actionType = it.actionType, target = it.target,
                    trackingViewData = trackingViewDataMapper.executeMapping(type.tracking),
                    clickTrackingUrls = it.clickTrackingUrls,
                    silo = it.silo,
                    sort = it.sortBy,
                    predicate = it.predicate
            )
        }
    }
}