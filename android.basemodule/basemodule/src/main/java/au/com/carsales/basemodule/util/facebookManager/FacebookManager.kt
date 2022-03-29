package au.com.carsales.basemodule.util.facebookManager

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*

class FacebookManager (val activity: Activity,
                       val callBackManager: CallbackManager,
                       val signInMainBtnFacebook: LoginButton,
                       private val facebookCallback: FacebookCallback<LoginResult>?,
                       private val graphCallBack: GraphRequest.GraphJSONObjectCallback?) {

    private val facebookPermissionsArray = arrayOf("email", "user_posts", "public_profile")
    private var mCallbackManager: CallbackManager? = null
    var fragment: androidx.fragment.app.Fragment? = null
    init {
        mCallbackManager = callBackManager
    }

    fun startFacebook(fragment: androidx.fragment.app.Fragment) {
        this.fragment = fragment
        setFacebookSettings()
        LoginManager.getInstance()
                .logInWithReadPermissions(activity, Arrays.asList<String>(*facebookPermissionsArray))
    }

    private fun setFacebookSettings() {
        // [START initialize_fblogin]
        // Initialize Facebook Login button
        signInMainBtnFacebook.setReadPermissions("email", "public_profile")
        signInMainBtnFacebook.registerCallback(mCallbackManager, facebookCallback)
    }

    var token: AccessToken? = null

    // [START auth_with_facebook]
    fun handleFacebookData(loginResult: LoginResult) {
        token = loginResult.accessToken
        val requestGraph = GraphRequest.newMeRequest(
                token, graphCallBack)
        val parameters = Bundle()
        parameters.putString("fields", "id,first_name,last_name,email")
        requestGraph.parameters = parameters
        requestGraph.executeAsync()
    }
    // [END auth_with_facebook]


}