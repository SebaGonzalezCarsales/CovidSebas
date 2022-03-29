package au.com.carsales.basemodule.tracking.model

import au.com.carsales.basemodule.tracking.model.bi.BiViewData
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewData
import au.com.carsales.basemodule.tracking.model.facebook.FbViewData
import au.com.carsales.basemodule.tracking.model.ga.GaViewData
import au.com.carsales.basemodule.tracking.model.ga5.Ga5ViewData
import au.com.carsales.basemodule.tracking.model.krux.KruxFireEventViewData
import au.com.carsales.basemodule.tracking.model.krux.KruxViewData
import java.io.Serializable

/**
 * Created by joseignacio on 3/7/18.
 */
data class TrackingViewData(

        var fabricViewData: FabricViewData? = null,
        //for enquiry api, and hmc
        var fabricsViewData: List<FabricViewData>? = null,

        var gaViewData: GaViewData? = null,
        var ga5ViewData: Ga5ViewData? = null,
        var fbViewData: FbViewData? = null,
        var biViewData: BiViewData? = null,
        var kruxViewDataList: MutableList<KruxViewData>? = null,
        var kruxFireEventViewDataList: MutableList<KruxFireEventViewData>? = null
) : Serializable