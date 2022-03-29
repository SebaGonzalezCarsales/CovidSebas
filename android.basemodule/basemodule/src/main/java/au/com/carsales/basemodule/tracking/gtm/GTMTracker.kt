package au.com.carsales.basemodule.tracking.gtm

import android.content.Context
import android.text.TextUtils
import android.util.Log
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.tagmanager.TagManager
import java.util.*
import java.util.concurrent.TimeUnit


class GTMTracker(var context: Context, var containerId: String, defaultContainerResId: Int,
                 debugMode: Boolean) {
    private val TAG = "GTMTracker"
    private val TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS: Long = 2000
    private var mTagManager: TagManager? = null


    /**
     * Initialize Google Tag Manager
     */

    init {
        Log.d(TAG, "init start")

        if (!TextUtils.isEmpty(containerId)) {


            mTagManager = TagManager.getInstance(context)
            mTagManager!!.setVerboseLoggingEnabled(debugMode)
            /*
            The ContainerHolder will be available from the returned PendingResult as soon as one of the following happens:
                a saved container which has been recently refreshed is loaded,
                a network container is loaded or a network error occurs, or
                a timeout occurs (2-second timeout)
             */
            val pending = mTagManager!!.loadContainerPreferNonDefault(containerId, defaultContainerResId)
            pending.setResultCallback(ResultCallback { containerHolder ->
                //Unsuccessful
                if (!containerHolder.status.isSuccess) {
                    Log.e(TAG, "failure loading container")
                    return@ResultCallback
                }
                //refresh container holder
                containerHolder.container//this line is necessary to get the latest container
            }, TIMEOUT_FOR_CONTAINER_OPEN_MILLISECONDS, TimeUnit.MILLISECONDS)
            Log.d(TAG, "init finish")
        }

    }

    /**
     * Push event with the given event key, Tags that match that event will fire.
     */
    fun pushEvent(map: Map<String, Any>) {

        if (TextUtils.isEmpty(containerId)) return

        val dataLayer = mTagManager!!.dataLayer
        dataLayer.push(map)
        Log.d(TAG, "pushEvent: " + map.toString())
        val cleanMap = HashMap<String, Any>()

        for ((key) in map) {
            cleanMap[key] = ""
        }
        dataLayer.push(cleanMap)
    }

    fun pushEvent(tags: List<GaViewData.Items?>?) {

        if (TextUtils.isEmpty(containerId)) return

        val gtmMap = HashMap<String, Any>()
        if (tags != null) {
            for (tag in tags) {
                if (tag?.key != null && tag?.value != null) {
                    gtmMap.put(tag?.key, tag?.value!!)
                }
            }
            pushEvent(gtmMap)
        }
    }
}

