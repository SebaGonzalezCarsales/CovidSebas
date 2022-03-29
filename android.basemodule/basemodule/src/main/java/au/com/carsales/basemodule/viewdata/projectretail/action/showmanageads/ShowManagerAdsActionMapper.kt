package au.com.carsales.basemodule.viewdata.projectretail.action.showmanageads

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.ShowManagerAdsActionData
import javax.inject.Inject

/**
 * Created by Dan on 03, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class ShowManagerAdsActionMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<ShowManagerAdsActionViewData?, ShowManagerAdsActionData?> {

    override fun executeMapping(type: ShowManagerAdsActionData?): ShowManagerAdsActionViewData? {
        return type?.let {
            ShowManagerAdsActionViewData(
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