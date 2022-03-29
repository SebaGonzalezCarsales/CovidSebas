package au.com.carsales.basemodule.util.facebookManager

import com.facebook.AccessToken
import org.json.JSONObject

data class UserLoginFacebook(val id:String? = null,
                             val firstName: String? = null,
                             val lastName: String? = null,
                             val email: String? = null,
                             val token: AccessToken? = null){

    companion object {
        const val ID = "id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val MAIL = "email"
    }

    fun getUserFacebook(jsonObject: JSONObject, token: AccessToken?): UserLoginFacebook{
        return  UserLoginFacebook(
                jsonObject.getString(ID),
                jsonObject.getString(FIRST_NAME),
                jsonObject.getString(LAST_NAME),
                jsonObject.getString(MAIL),
                token
        )

    }

}