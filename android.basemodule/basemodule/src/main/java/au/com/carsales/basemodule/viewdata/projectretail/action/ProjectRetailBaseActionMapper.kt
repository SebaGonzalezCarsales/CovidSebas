package au.com.carsales.basemodule.viewdata.projectretail.action

import au.com.carsales.basemodule.api.data.Mapper
import au.com.carsales.basemodule.api.data.commons.BaseActionData
import au.com.carsales.basemodule.api.data.projectretail.ProjectRetailBaseAPIGSONManager
import au.com.carsales.basemodule.api.data.projectretail.action.*
import au.com.carsales.basemodule.api.data.projectretail.action.weblink.WebLinkAction
import au.com.carsales.basemodule.baseactionviewdata.ActionViewData
import au.com.carsales.basemodule.tracking.model.TrackingViewDataMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.myaccount.MyAccountActionViewData
import au.com.carsales.basemodule.viewdata.projectretail.action.openinbox.OpenInboxActionMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showeditad.ShowEditAdActionMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showhome.ShowHomeActionVDMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showitemdetails.ShowItemDetailsActionVDMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showlisting.ShowListingActionVDMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showmanageads.ShowManagerAdsActionMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showsaveditems.ShowSavedItemsActionVDMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.showsavedsearches.ShowSavedSearchesActionVDMapper
import au.com.carsales.basemodule.viewdata.projectretail.action.weblink.WebLinkActionVDMapper
import javax.inject.Inject

class ProjectRetailBaseActionMapper @Inject constructor(
        private val trackingViewDataMapper: TrackingViewDataMapper,
        private val showListingActionVDMapper: ShowListingActionVDMapper,
        private val showItemDetailsActionVDMapper: ShowItemDetailsActionVDMapper,
        private val managerAdsActionMapper: ShowManagerAdsActionMapper,
        private val showHomeActionVDMapper: ShowHomeActionVDMapper,
        private val showSavedItemsActionVDMapper: ShowSavedItemsActionVDMapper,
        private val showSavedSearchesActionVDMapper: ShowSavedSearchesActionVDMapper,
        private val webLinkActionVDMapper: WebLinkActionVDMapper,
        private val openInboxActionMapper: OpenInboxActionMapper,
        private val showEditAdActionMapper: ShowEditAdActionMapper
) : Mapper<ActionViewData?, BaseActionData?> {

    override fun executeMapping(type: BaseActionData?): ActionViewData? {
        return when (type?.actionType) {
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_MESSAGE,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_ADD_CAR,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_BARCODE,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_VEHICLES,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_MEMBER_BENEFITS -> {

                val myAccountData = type as MyAccountActionData

                val myAccountViewData = MyAccountActionViewData(
                        actionType = myAccountData.actionType,
                        url = myAccountData.url,
                        actionName = myAccountData.actionName,
                        openInExternalBrowser = myAccountData.openInExternalBrowser,
                        openInPopup = myAccountData.openInPopup,
                        trackingViewData = myAccountData.tracking?.let { trackingViewDataMapper.executeMapping(it) },
                        product = myAccountData.product,
                        id = myAccountData.id,
                        tabKey = myAccountData.tabKey,
                        conversationId = myAccountData.conversationId)


                myAccountViewData.actionType = type.actionType

                myAccountViewData
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_ITEM_DETAILS -> {
                showItemDetailsActionVDMapper.executeMapping(type as ShowItemDetailAction)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_LISTING -> {
                showListingActionVDMapper.executeMapping(type as ShowListingAction)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_MANAGE_ADS -> {
                managerAdsActionMapper.executeMapping(type as? ShowManagerAdsActionData)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_SAVED_ITEMS -> {
                showSavedItemsActionVDMapper.executeMapping(type as? ShowSavedItemsActionData)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_SAVED_SEARCHES -> {
                showSavedSearchesActionVDMapper.executeMapping(type as? ShowSavedSearchActionData)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_HOME_PAGE -> {
                showHomeActionVDMapper.executeMapping(type as? ShowHomeActionData)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_WEB_LINK -> {
                webLinkActionVDMapper.executeMapping(type as WebLinkAction)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_OPEN_INBOX -> {
                openInboxActionMapper.executeMapping(type as? OpenInboxActionData)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_EDIT_AD -> {
                showEditAdActionMapper.executeMapping(type as ShowEditAdActionData)
            }
            else -> {
                if (!type?.actionType.isNullOrEmpty()) {
                    ActionViewData(
                            actionType = type?.actionType,
                            target = type?.target,
                            trackingViewData = trackingViewDataMapper.executeMapping(type?.tracking),
                            clickTrackingUrls = type?.clickTrackingUrls,
                            url = type?.url
                    )
                } else {
                    null
                }

            }
        }
    }
}