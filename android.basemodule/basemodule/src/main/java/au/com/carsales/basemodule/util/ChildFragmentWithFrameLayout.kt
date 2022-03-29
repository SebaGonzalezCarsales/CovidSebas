package au.com.carsales.basemodule.util

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ChildFragmentWithFrameLayout : FrameLayout {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
    }

    fun setChildFragment(childFragmentManager: FragmentManager, fragmentChild: Fragment) {
        childFragmentManager.beginTransaction().replace(this.id, fragmentChild).commitNow()
    }

    fun updateDataInChildFragment(){

    }
}