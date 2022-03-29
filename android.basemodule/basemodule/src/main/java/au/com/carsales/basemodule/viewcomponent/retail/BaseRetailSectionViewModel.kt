package au.com.carsales.basemodule.viewcomponent.retail

import android.graphics.Color
import au.com.carsales.basemodule.extension.checkHashTagSimbolInColor
import au.com.carsales.basemodule.tracking.model.TrackingViewData
import au.com.carsales.basemodule.util.dpToPx
import au.com.carsales.basemodule.util.viewModel.BaseViewModel
import au.com.carsales.basemodule.viewdata.projectretail.section.SectionViewData

open class BaseRetailSectionViewModel(
    private val sectionViewData: SectionViewData? = null,
    ignoreRootPadding: Boolean? = false
) : BaseViewModel() {


    var sectionType: String? = null
    var isFormSection = false
    var isViewTrackingSent = false
    var viewTrackingUrl: String? = null
    var backgroundColour: String? = null
    var separatorType: String? = null
    var separatorColour: String? = null
    var paddingType: String? = null
    var trackingViewData: TrackingViewData? = null
    var backgroundColourInt: Int? = Color.WHITE
    var separatorColourInt: Int? = Color.WHITE
    var sectionPadding = 0

    var paddingTopBottom: Int = dpToPx(12)

    var ignoreRootPadding = false
    private val paddingLeftRightSmall: Int
        get() = dpToPx(16)
    private val paddingLeftRightLarge: Int
        get() = dpToPx(32)

    var rootContainerPadding = 0
    var separatorPadding = 0

    init {
        this.ignoreRootPadding = ignoreRootPadding ?: false
        setData(sectionViewData, this.ignoreRootPadding)
    }


    fun setData(sectionViewData: SectionViewData?, ignoreRootPadding: Boolean = false) {
        if (sectionViewData == null) {
            return
        }
        this.ignoreRootPadding = ignoreRootPadding
        this.sectionType = sectionViewData?.sectionType
        this.isFormSection = sectionViewData?.isFormSection ?: false

        //background
        this.backgroundColour = sectionViewData?.backgroundColour
        try {
            this.backgroundColourInt =
                Color.parseColor(sectionViewData?.backgroundColour?.checkHashTagSimbolInColor())

        } catch (e: Exception) {
            //ignore
        }

        //separator type
        this.separatorType = sectionViewData?.separatorType
        //separator colour
        this.separatorColour = sectionViewData?.separatorColour
        try {
            this.separatorColourInt =
                Color.parseColor(sectionViewData?.separatorColour?.checkHashTagSimbolInColor())

        } catch (e: Exception) {
            //ignore
        }

        //padding type
        this.paddingType = sectionViewData?.paddingType
        //padding
        sectionPadding = when (sectionViewData?.paddingType) {
            SectionViewData.PaddingType.SMALL.value ->
                paddingLeftRightSmall

            else -> {
                0

            }
        } ?: 0
        rootContainerPadding = if (ignoreRootPadding) {
            0
        } else {
            sectionPadding
        }
        //separator padding
        separatorPadding = when (sectionViewData.separatorType) {
            SectionViewData.SeparatorType.PADDED.value -> {
                sectionPadding

            }
            else -> 0
        }
        //tracking
        this.viewTrackingUrl = sectionViewData?.viewTrackingUrl
        this.trackingViewData = sectionViewData?.trackingViewData
    }

    fun trackViewedIfNeeded() {
        if (!isViewTrackingSent && viewTrackingUrl != null) {
            baseTracker.trackUrls(viewTrackingUrl)
            isViewTrackingSent = true
        }


    }

}