package au.com.carsales.basemodule.viewdata.projectretail.action.showitemdetails

import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.util.Mapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowItemDetailAction
import javax.inject.Inject

class ShowItemDetailsActionVDMapper @Inject constructor(
    private val trackingViewDataMapper: TrackingViewDataMapper
) : Mapper<ShowItemDetailsActionVD, ShowItemDetailAction> {
    override fun executeMapping(type: ShowItemDetailAction?): ShowItemDetailsActionVD? {
        return type?.let {

            ShowItemDetailsActionVD(
                actionType = it.actionType,
                target = it.target,
                trackingViewData = trackingViewDataMapper.executeMapping(it.tracking),
                clickTrackingUrls = it.clickTrackingUrls,
                silo = it.silo,
                id = it.id
            )
        }
    }
}