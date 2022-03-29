package au.com.carsales.basemodule.tracking.fabricAutogate

import android.content.Context
import au.com.carsales.basemodule.BuildConfig
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewData

class FabricTracker(context: Context) {


    companion object {

        private var instance: FabricTracker? = null
        fun getInstance(context: Context): FabricTracker {
            if (instance == null) {
                instance = FabricTracker(context)
            }
            return instance as FabricTracker
        }
    }

    init {
        //Fabric.with(context, Answers())
    }

    fun track(fabricModel: FabricViewData?) {
        if (fabricModel == null) return
        if (fabricModel.event == null) return

        if (BuildConfig.BUILD_TYPE != "debug") {
          //  val customEvent = CustomEventWithAttributes(fabricModel.event, fabricModel.attributes)
            //Answers.getInstance().logCustom(customEvent)
        }
    }


}