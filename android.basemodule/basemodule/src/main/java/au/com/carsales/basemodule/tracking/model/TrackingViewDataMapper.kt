package au.com.carsales.basemodule.tracking.model

import au.com.carsales.basemodule.api.data.commons.tracking.TrackingData
import au.com.carsales.basemodule.tracking.model.bi.BiViewDataMapper
import au.com.carsales.basemodule.tracking.model.fabric.FabricViewDataMapper
import au.com.carsales.basemodule.tracking.model.facebook.FbViewDataMapper
import au.com.carsales.basemodule.tracking.model.ga.GaViewDataMapper
import au.com.carsales.basemodule.tracking.model.ga5.Ga5ViewDataMapper
import au.com.carsales.basemodule.tracking.model.krux.KruxFireEventViewDataMapper
import au.com.carsales.basemodule.tracking.model.krux.KruxViewDataMapper

import au.com.carsales.basemodule.util.Mapper
import javax.inject.Inject

/**
 * Created by joseignacio on 3/7/18.
 */
open class TrackingViewDataMapper @Inject constructor(private val fabricViewDataMapper: FabricViewDataMapper,
                                                      private val gaViewDataMapper: GaViewDataMapper,
                                                      private val ga5ViewDataMapper: Ga5ViewDataMapper,
                                                      private val fbViewDataMapper: FbViewDataMapper,
                                                      private val biViewDataMapper: BiViewDataMapper,
                                                      private val kruxViewDataMapper: KruxViewDataMapper,
                                                      private val kurxFireEventViewDataMapper: KruxFireEventViewDataMapper)
    : Mapper<TrackingViewData, TrackingData> {

    override fun executeMapping(type: TrackingData?): TrackingViewData? {

        return if (type != null) {
            TrackingViewData(
                    fabricViewData = type.fabricData?.let { fabricViewDataMapper.executeMapping(it) },
                    fabricsViewData = type.fabrics?.mapNotNull { fabricViewDataMapper.executeMapping(it) },
                    gaViewData = type.gaData?.let { gaViewDataMapper.executeMapping(it) },
                    ga5ViewData = type.ga5Data?.let { ga5ViewDataMapper.executeMapping(it) },
                    fbViewData = type.fbData?.let { fbViewDataMapper.executeMapping(it) },
                    biViewData = type.biData?.let { biViewDataMapper.executeMapping(it) },
                    kruxViewDataList = type.kruxDataList?.mapNotNull { kruxViewDataMapper.executeMapping(it) }?.toMutableList(),
                    kruxFireEventViewDataList = type.kurxFireEvents?.mapNotNull { kurxFireEventViewDataMapper.executeMapping(it) }?.toMutableList()
            )
        } else null

    }
}