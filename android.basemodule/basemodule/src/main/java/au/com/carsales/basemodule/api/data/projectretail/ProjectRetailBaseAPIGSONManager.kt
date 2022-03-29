package au.com.carsales.basemodule.api.data.projectretail

import androidx.annotation.StringDef
import au.com.carsales.basemodule.api.data.commons.BaseActionData
import au.com.carsales.basemodule.api.data.projectretail.deserializer.ProjectRetailBaseActionDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ProjectRetailBaseAPIGSONManager private constructor() {

    @Retention(AnnotationRetention.SOURCE)
    annotation class ActionType


    private fun createGson(): Gson {
        val gsonBuilder: GsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun createGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(BaseActionData::class.java, ProjectRetailBaseActionDeserializer())

        return gsonBuilder
    }

    companion object {
        private var instance: ProjectRetailBaseAPIGSONManager? = null
        //deep link actions
        const val ACTION_MY_ACCOUNT_ADD_CAR = "addCar"
        const val ACTION_MY_ACCOUNT_SHOW_BARCODE = "showBarcode"
        const val ACTION_MY_ACCOUNT_SHOW_VEHICLES = "showVehicles"
        const val ACTION_MY_ACCOUNT_SHOW_MEMBER_BENEFITS = "showMemberBenefits"
        const val ACTION_MY_ACCOUNT_SHOW_MESSAGE = "showMessages"
        const val ACTION_SHOW_SAVED_ITEMS = "showSavedItems"
        const val ACTION_SHOW_SAVED_SEARCHES = "showSavedSearches"
        const val ACTION_SHOW_ITEM_DETAILS = "showItemDetails"
        const val ACTION_SHOW_HOME_PAGE = "showHomePage"
        const val ACTION_SHOW_LISTING = "showListing"
        const val ACTION_SHOW_MANAGE_ADS = "showManageAds"

        const val ACTION_WEB_LINK = "webLink"

        const val ACTION_OPEN_INBOX = "openInbox"
        const val ACTION_PURCHASE_ADVERT_PRODUCT = "purchaseAdvertProduct"
        const val ACTION_SHOW_EDIT_AD = "showEditAd"

        fun create(): Gson {
            if (instance == null) {
                instance = ProjectRetailBaseAPIGSONManager()
            }
            return instance!!.createGson()
        }

        fun createGsonBuilder(): GsonBuilder {
            if (instance == null) {
                instance = ProjectRetailBaseAPIGSONManager()
            }
            return instance!!.createGsonBuilder()
        }
    }
}