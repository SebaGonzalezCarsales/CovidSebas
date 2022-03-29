package au.com.carsales.basemodule.api.data.commons.tracking

import au.com.carsales.basemodule.api.data.commons.tracking.bi.BIData
import au.com.carsales.basemodule.api.data.commons.tracking.fabric.FabricData
import au.com.carsales.basemodule.api.data.commons.tracking.facebook.FbData
import au.com.carsales.basemodule.api.data.commons.tracking.ga.GaData
import au.com.carsales.basemodule.api.data.commons.tracking.ga5.Ga5Data
import au.com.carsales.basemodule.api.data.commons.tracking.krux.KruxData
import au.com.carsales.basemodule.api.data.commons.tracking.krux.KruxFireEvent
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by joseignacio on 3/6/18.
 */
class TrackingData(

        @field:SerializedName("fb")
        var fbData: FbData? = null,

        @field:SerializedName("ga")
        var gaData: GaData? = null,

        @field:SerializedName("ga5")
        var ga5Data: Ga5Data? = null,

        @field:SerializedName("bi")
        var biData: BIData? = null,

        @field:SerializedName("krux")
        var kruxDataList: List<KruxData>? = null,

        @field:SerializedName("fabric")
        val fabricData: FabricData? = null,

        @field:SerializedName("fabrics")
        var fabrics: List<FabricData>? = null,

        @SerializedName("kruxFireEvents")
        val kurxFireEvents: List<KruxFireEvent>? = null

) : Serializable