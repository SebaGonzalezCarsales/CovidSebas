package au.com.carsales.basemodule.viewcomponent

import android.content.Context
import androidx.annotation.FontRes
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.TextView
import au.com.carsales.basemodule.extension.setFontType
import au.com.carsales.basemodule.tracking.Tracker
import org.w3c.dom.Text

abstract class BaseSectionComponent : ConstraintLayout {
    var isFormSection: Boolean = false
    protected var mBaseSectionViewModel: BaseSectionViewModel? = null
    val baseTracker = Tracker()

    val sectionType: String?
        get() = if (mBaseSectionViewModel != null) mBaseSectionViewModel!!.sectionType else ""

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, sectionViewModel: BaseSectionViewModel) : super(context) {
        mBaseSectionViewModel = sectionViewModel
        isFormSection = sectionViewModel.isFormSection
    }

    open fun updateComponent(viewModel: BaseSectionViewModel) {
        mBaseSectionViewModel = viewModel
    }

    companion object {
        val BUNDLE_KEY_SUPER_STATE = "Superstate"
        private val TAG = BaseSectionComponent::class.java.simpleName
    }


    fun setViewFontSizeSP(view: TextView, spSz: Float) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSz)
    }

    fun setViewFontType(view: TextView, @FontRes fontRes: Int) {
        view.setFontType(fontRes)
    }

    fun setViewColor(view: TextView, color: Int) {
        view.setTextColor(color)
    }

    fun setViewGravity(view: TextView, gravity: Int) {
        view.setGravity(gravity)
    }

    fun setViewPadding(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        view.setPadding(left, top, right, bottom)
    }

    open fun trackViewedIfNeeded() {

        mBaseSectionViewModel?.let {
            if (!it.isViewTrackingSent && it.viewTrackingUrl != null) {
                it.isViewTrackingSent = true
                //SASTracking.track(mBaseSectionViewModel.viewTrackingUrl);
                baseTracker.trackUrls(it.viewTrackingUrl, null)
                Log.d(TAG, "Send view tracking : " + it.viewTrackingUrl)
            }
        }
    }


}
