package au.com.carsales.basemodule.tracking.facebook

import android.content.Context
import android.os.Bundle
import au.com.carsales.basemodule.BuildConfig
import au.com.carsales.basemodule.tracking.model.facebook.FbViewData
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import java.util.*


/**
 * Created by kelly.wang on 7/12/17.
 */

class FacebookTracker(context: Context) {
    private val TAG = FacebookTracker::class.java.simpleName
    private var logger: AppEventsLogger? = null


    companion object {
        private var instance: FacebookTracker? = null
        fun getInstance(context: Context): FacebookTracker {
            if (instance == null) {
                instance = FacebookTracker(context)
            }
            return instance as FacebookTracker
        }
    }

    init {
        if (logger == null) {
            logger = AppEventsLogger.newLogger(context)
            if (BuildConfig.DEBUG) {
                FacebookSdk.setIsDebugEnabled(true);
                FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
            }
        }
    }


    fun track(fbModel: FbViewData?) {
        if (logger == null || fbModel == null) return

        val parameters = Bundle()

        if (fbModel.attributes != null) {
            for ((key, value) in fbModel.attributes) {
                parameters.putString(key, value)
            }
        }

        logger!!.logEvent(fbModel.event, parameters)


    }


    //All fb data is driven by app-car api,
    //To avoid hassle membership api to add fb data, and since it is the only one app-car api cann't cover, so hardcode here
    fun logFBSignUpEvent() {
        val fbDataView = FbViewData("Membership Sign Up", HashMap())
        track(fbDataView)
    }
}
