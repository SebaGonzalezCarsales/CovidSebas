package au.com.carsales.basemodule.api.data.projectretail.action

import androidx.annotation.Keep
import au.com.carsales.basemodule.api.data.commons.BaseActionData
import com.google.gson.annotations.SerializedName

/**
 * Created by anibalbastias on 2019-06-04.
 *
 * MyAccountActionData is used in nativation/my_account_nav_graph.xml, add @Keep annotation to prevent from being obfuscated.
 * According to <a href="https://developer.android.com/guide/navigation/navigation-pass-data#proguard_considerations">navigation-pass-data</a>
 * If you are shrinking your code,
 * you need to prevent your Parcelable, Serializable, and Enum class names from being obfuscated as part of the minification process. :
 */

@Keep
data class MyAccountActionData(

        @field:SerializedName("actionName")
        var actionName: String? = null,

        @field:SerializedName("openInExternalBrowser")
        var openInExternalBrowser: Boolean? = null,

        @field:SerializedName("openInPopup")
        var openInPopup: Boolean? = null,

        @field:SerializedName("product")
        var product: String? = null,

        @field:SerializedName("id")
        var id: String? = null,

        @field:SerializedName("tabKey")
        val tabKey: String? = null,

        @field:SerializedName("conversationId")
        val conversationId: String? = null
) : BaseActionData()