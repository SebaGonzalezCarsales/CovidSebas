package au.com.carsales.basemodule.tracking.model.fabric

import au.com.carsales.basemodule.api.data.commons.tracking.fabric.ActionData
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by igor.roman on 09-02-18.
 */
@Deprecated(message = "")
class ActionDataViewMapper @Inject constructor(private val trackingViewDataMapper: TrackingViewDataMapper)
    : Mapper<ActionDataView, ActionData> {

    override fun executeMapping(type: ActionData?): ActionDataView? {
        return type?.let {
            ActionDataView(type.target,
                    type.actionType,
                    type.silo,
                    type.predicate,
                    type.url,
                    type.openInExternalBrowser,
                    type.trackingData?.let { trackingViewDataMapper.executeMapping(it) },
                    type.contactNumberForDialling,
                    type.contactNumberForDisplay
            )
        }

    }

}