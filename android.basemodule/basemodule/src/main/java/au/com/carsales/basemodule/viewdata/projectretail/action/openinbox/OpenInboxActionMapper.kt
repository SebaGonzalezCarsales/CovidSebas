/*
 *
 *  * Created by Iván Moya on 10-05-19 16:36
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10-05-19 16:35
 *
 */

package au.com.carsales.basemodule.viewdata.projectretail.action.openinbox

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.api.data.projectretail.action.OpenInboxActionData
import javax.inject.Inject

/**
 * Created by Dan on 03, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class OpenInboxActionMapper @Inject constructor(private val trackingDataViewMapper: TrackingViewDataMapper) : Mapper<OpenInboxActionViewData?, OpenInboxActionData?> {

    override fun executeMapping(type: OpenInboxActionData?): OpenInboxActionViewData? {

        return type?.let {
            OpenInboxActionViewData(
                    actionType = it.actionType,
                    target = it.target,
                    trackingViewData = it.tracking.let { tracking ->
                        trackingDataViewMapper.executeMapping(tracking)
                    },
                    clickTrackingUrls = it.clickTrackingUrls,
                    conversationId = it.conversationId,
                    tabKey = it.tabKey,
                    url = it.url
            )
        }


    }
}