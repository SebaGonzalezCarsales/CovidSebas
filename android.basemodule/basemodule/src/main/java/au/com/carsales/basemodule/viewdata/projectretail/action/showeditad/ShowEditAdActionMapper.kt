package au.com.carsales.basemodule.viewdata.projectretail.action.showeditad

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowEditAdActionData
import javax.inject.Inject

/**
 * Created by Dan on 03, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class ShowEditAdActionMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<ShowEditAdActionViewData?, ShowEditAdActionData?> {

    override fun executeMapping(type: ShowEditAdActionData?): ShowEditAdActionViewData? {
        return type?.let {
            ShowEditAdActionViewData(
                    actionType = it.actionType,
                    target = it.target,
                    trackingViewData = it.tracking.let {
                        trackingDataViewMapper.executeMapping(it)
                    },
                    clickTrackingUrls = it.clickTrackingUrls,
                    url = it.url,
                    advertId = it.advertId)
        }
    }
}