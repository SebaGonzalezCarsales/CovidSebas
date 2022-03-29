package au.com.carsales.basemodule.api.data.projectretail.action

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName

/**
 * Created by Dan on 02, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class OpenInboxActionData : BaseActionData() {

        @field:SerializedName("conversationKey")
        val conversationId: String? = null

        @field:SerializedName("tabKey")
        val tabKey: String? = null

}