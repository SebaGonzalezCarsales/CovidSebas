package au.com.carsales.basemodule.viewcomponent.retail

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.invisible
import au.com.carsales.basemodule.extension.visible
import au.com.carsales.basemodule.viewdata.projectretail.section.SectionViewData
import kotlinx.android.synthetic.main.view_component_section_base.view.*

open class BaseRetailSectionViewComponent : RelativeLayout {
    protected var sectionViewModel: BaseRetailSectionViewModel? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }


    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context, attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.view_component_section_base, this)
        layoutParams = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun getSectionPadding(): Int = sectionViewModel?.sectionPadding ?: 0

    fun addToRootContainer(view: View?) {
        if (view == null) {
            return
        }
        view.id = View.generateViewId()
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params

        this.addView(view, 0)
        (sectionBaseSeparator.layoutParams as RelativeLayout.LayoutParams).addRule(BELOW, view.id)


    }

    fun setViewModel(sectionViewModel: BaseRetailSectionViewModel?) {
        this.sectionViewModel = sectionViewModel


        //background
        this.setBackgroundColor(sectionViewModel?.backgroundColourInt ?: Color.WHITE)

        //padding

        getChildAt(0)?.setPadding(
            sectionViewModel?.rootContainerPadding ?: 0,
            sectionViewModel?.paddingTopBottom ?: 0,
            sectionViewModel?.rootContainerPadding ?: 0,
            sectionViewModel?.paddingTopBottom ?: 0
        )

        //separator
        //separator padding
        (sectionBaseSeparator.layoutParams as RelativeLayout.LayoutParams).setMargins(
            sectionViewModel?.separatorPadding ?: 0, 0,
            sectionViewModel?.separatorPadding ?: 0, 0
        )
        sectionBaseSeparator.setBackgroundColor(sectionViewModel?.separatorColourInt ?: Color.WHITE)

        when (sectionViewModel?.separatorType) {
            SectionViewData.SeparatorType.NONE.value ->
                sectionBaseSeparator.invisible()
            SectionViewData.SeparatorType.PADDED.value -> {
                sectionBaseSeparator.visible()


            }
        }

        this.invalidate()
    }

    open fun saveViewState() {

    }

    open fun trackViewedIfNeeded() {
        sectionViewModel?.trackViewedIfNeeded()
    }
}