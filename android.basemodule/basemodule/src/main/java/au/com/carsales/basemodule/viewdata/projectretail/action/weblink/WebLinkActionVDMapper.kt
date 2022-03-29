package au.com.carsales.basemodule.viewdata.projectretail.action.weblink

import au.com.carsales.basemodule.api.data.projectretail.action.weblink.WebLinkAction
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.util.Mapper
import au.com.carsales.basemodule.viewdata.projectretail.action.weblink.additionalparams.AdditionalUrlParametersVDMapper
import javax.inject.Inject

class WebLinkActionVDMapper @Inject constructor(
    private val trackingViewDataMapper: TrackingViewDataMapper,
    private val additionalUrlParametersVDMapper: AdditionalUrlParametersVDMapper
) : Mapper<WebLinkActionVD, WebLinkAction> {
    override fun executeMapping(type: WebLinkAction?): WebLinkActionVD? {
        return type?.let {
            WebLinkActionVD(
                actionType = it.actionType, target = it.target,
                trackingViewData = trackingViewDataMapper.executeMapping(it.tracking),
                clickTrackingUrls = it.clickTrackingUrls,
                silo = type.silo,
                url = it.url,
                inAppBrowserTitle = it.inAppBrowserTitle,
                openInExternalBrowser = it.openInExternalBrowser,
                isPDF = it.isPDF,
                additionalUrlParametersViewData = additionalUrlParametersVDMapper.executeMapping(
                    it.additionalUrlParameters
                )
            )

        }
    }
}