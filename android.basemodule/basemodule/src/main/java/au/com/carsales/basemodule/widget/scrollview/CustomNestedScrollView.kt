package au.com.carsales.basemodule.widget.scrollview

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by anibal.bastias on 03-04-2018
 */

class CustomNestedScrollView : NestedScrollView {

    var isEnableScrolling = true

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        return if (isEnableScrolling) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isEnableScrolling) {
            super.onTouchEvent(ev)
        } else {
            false
        }
    }
}