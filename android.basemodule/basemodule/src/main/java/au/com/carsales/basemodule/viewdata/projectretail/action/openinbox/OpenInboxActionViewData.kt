/*
 *
 *  * Created by Iván Moya on 10-05-19 16:37
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10-05-19 16:37
 *
 */

package au.com.carsales.basemodule.viewdata.projectretail.action.openinbox

import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewData

/**
 * Created by Dan on 03, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class OpenInboxActionViewData(actionType: String? = null,
                              target: String? = null,
                              trackingViewData: TrackingViewData? = null,
                              clickTrackingUrls: List<String>? = null,
                              fabric: FabricViewData? = null,
                              tracking: TrackingViewData? = null,
                              val conversationId: String? = null,
                              val tabKey: String? = null,
                              url: String? = null) : ActionViewData(actionType, target, trackingViewData, clickTrackingUrls, url)