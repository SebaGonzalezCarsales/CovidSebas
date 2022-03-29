package au.com.carsales.basemodule.api.data.projectretail.deserializer

import au.com.carsales.basemodule.api.data.commons.BaseActionData
import au.com.carsales.basemodule.api.data.projectretail.ProjectRetailBaseAPIGSONManager
import au.com.carsales.basemodule.api.data.projectretail.action.*
import au.com.carsales.basemodule.api.data.projectretail.action.weblink.WebLinkAction
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

open class ProjectRetailBaseActionDeserializer : JsonDeserializer<BaseActionData> {

    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
    ): BaseActionData? {

        var type = json?.asJsonObject?.get("actionType")?.asString
        var ret: BaseActionData? = null

        when (type) {
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_MESSAGE,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_ADD_CAR,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_BARCODE,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_VEHICLES,
            ProjectRetailBaseAPIGSONManager.ACTION_MY_ACCOUNT_SHOW_MEMBER_BENEFITS -> {
                ret = context?.deserialize<MyAccountActionData>(json,
                        MyAccountActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_LISTING -> {
                ret = context?.deserialize<ShowListingAction>(json,
                        ShowListingAction::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_ITEM_DETAILS -> {
                ret = context?.deserialize<ShowItemDetailAction>(json,
                        ShowItemDetailAction::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_MANAGE_ADS -> {
                ret = context?.deserialize<ShowManagerAdsActionData>(json,
                        ShowManagerAdsActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_SAVED_ITEMS -> {
                ret = context?.deserialize<ShowSavedItemsActionData>(json,
                        ShowSavedItemsActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_SAVED_SEARCHES -> {
                ret = context?.deserialize<ShowSavedSearchActionData>(json,
                        ShowSavedSearchActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_HOME_PAGE -> {
                ret = context?.deserialize<ShowHomeActionData>(json,
                        ShowHomeActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_WEB_LINK -> {
                ret = context?.deserialize<WebLinkAction>(json,
                        WebLinkAction::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_OPEN_INBOX -> {
                ret = context?.deserialize<OpenInboxActionData>(json, OpenInboxActionData::class.java)
            }
            ProjectRetailBaseAPIGSONManager.ACTION_SHOW_EDIT_AD -> {
                ret = context?.deserialize<ShowEditAdActionData>(json, ShowEditAdActionData::class.java)
            }
            else -> {
                ret = BaseActionData()
                ret.actionType = type
                ret.url = json?.asJsonObject?.get("url")?.asString
            }
        }

        return ret
    }
}