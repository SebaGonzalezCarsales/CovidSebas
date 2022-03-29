package au.com.carsales.basemodule.abtest

import au.com.carsales.basemodule.getAppContext
import com.optimizely.ab.android.sdk.OptimizelyManager
import com.optimizely.ab.config.Variation
import androidx.annotation.RawRes
import au.com.carsales.basemodule.util.isPhone
import com.optimizely.ab.android.sdk.OptimizelyClient
import com.optimizely.ab.android.sdk.OptimizelyStartListener
import com.optimizely.ab.config.audience.match.MatchType.logger


/**
 * Created by kelly.wang on 2019-08-01.
 */
object ABTest {

    /*Vertical attribute key, value will be carsales/bikesales/chileautos/demotores/soloautos ... */
    const val ATTR_KEY_VERTICAL = "vertical"
    /*Platform attribute key, value will be android, (mobi/ios for other platform)*/
    const val ATTR_KEY_PLATFORM = "platform"
    /*Loggedin Attribute key, value will be true or false*/
    const val ATTR_KEY_LOGGEDIN = "loggedin"
    /*Device Attribute key, value will be phone or tablet*/
    const val ATTR_KEY_DEVICE = "device"
    /*MemberTrackingId Attribute key, value will be users memberTrackingId if user logged in*/
    const val ATTR_KEY_MEMBER_TRACKING_ID = "trackingid"

    private const val ATTR_VALUE_PLATFORM = "android"
    private const val ATTR_VALUE_DEVICE_PHONE = "phone"
    private const val ATTR_VALUE_DEVICE_TABLET = "tablet"

    private const val DATA_FILE_DOWNLOAD_INTERVAL = 60L * 10L // 10 minutes
    private const val EVENT_DISPATCH_INTERVAL = 60L * 10L // 10 minutes

    private var optimizelyClient: OptimizelyClient? = null
    private var tenant: String = "unknown"


    /**
     * @param tenant: carsales/bikesales/chileautos/demotores/soloautos
     * @param sdkKey: optimizely SDK Key
     * @param dataFilesRes: optimizely data file
     */
    fun initialize(tenant: String, sdkKey: String, @RawRes datafileRes: Int) {
        this.tenant = tenant
        val optimizelyManager = OptimizelyManager.builder().withEventDispatchInterval(EVENT_DISPATCH_INTERVAL)
                .withDatafileDownloadInterval(DATA_FILE_DOWNLOAD_INTERVAL)
                .withSDKKey(sdkKey).build(getAppContext())
        //instantiate asynchronously
        optimizelyManager?.initialize(getAppContext(), datafileRes) {
            optimizelyClient = it
        }

    }


    /**
     * @param experimentKey : experiment Key defined in Optimizely Console
     * @param appInstanceId: app instance id, as user id passed into Optimizely SDK
     * @param attributes:  A map of custom key-value string pairs specifying attributes for the user that are used for audience targeting and results segmentation.
     */
    fun activateExperiment(experimentKey: String, appInstanceId: String, attributes: MutableMap<String, Any>?): Variation? {
        if (optimizelyClient == null) {
            logger.error("optimizelyClient is null, did you forget to call initialize method?")
        }
        return if (attributes != null) {
            optimizelyClient?.activate(experimentKey, appInstanceId, attributes)
        } else {
            optimizelyClient?.activate(experimentKey, appInstanceId)
        }
    }

    /**
     * @param eventName event name defined in Optimizely Console
     * @param appInstanceId : appInstanceId
     * @param attributes: if is null use default Attributes
     */
    fun track(eventName: String, appInstanceId: String, attributes: MutableMap<String, Any>?) {
        if (attributes != null) {
            optimizelyClient?.track(eventName, appInstanceId, attributes)
        } else {
            optimizelyClient?.track(eventName, appInstanceId)
        }
    }

    /* example of attributes
    getAttributes() {
        let attributes = {
            vertical: this.routeHelperService.vertical,
            platform: 'mobi',
            loggedin: this.authService.isLoggedIn,
            device: '',
            memberTrackingId: this.authService.memberTrackingId
        };

        if (this.responsiveHelperService.isDesktop) {
            attributes.device = 'desktop';
        }

        if (this.responsiveHelperService.isTablet) {
            attributes.device = 'tablet';
        }

        if (this.responsiveHelperService.isPhone) {
            attributes.device = 'phone';
        }
    }*/
    /**
     * Consuming library have to call this method and add them to the attributes when calling activateExperiment and tracking
     */
    fun getDefaultAttributes(): MutableMap<String, Any> {
        val attributes = mutableMapOf<String, Any>()
        attributes[ATTR_KEY_VERTICAL] = this.tenant
        attributes[ATTR_KEY_PLATFORM] = ATTR_VALUE_PLATFORM
        attributes[ATTR_KEY_DEVICE] = if (isPhone(getAppContext())) ATTR_VALUE_DEVICE_PHONE else ATTR_VALUE_DEVICE_TABLET

        return attributes

    }

}