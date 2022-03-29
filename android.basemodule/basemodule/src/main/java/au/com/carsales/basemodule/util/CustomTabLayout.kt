package au.com.carsales.basemodule.util

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field

class CustomTabLayout : TabLayout {

    companion object{
        private const val WIDTH_INDEX = 0
        private const val DIVIDER_FACTOR = 3
        private const val SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth"
    }

    constructor(context: Context?) : super(context) {
        initTabMinWidth()
    }

    constructor(context: Context?, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        initTabMinWidth()

    }

    constructor(context: Context?, attributeSet: AttributeSet): super(context, attributeSet){
        initTabMinWidth()
    }

    private fun initTabMinWidth() {
        val wh: IntArray = Utils().getScreenSize(context)
        val tabMinWidth: Int = wh[WIDTH_INDEX] / DIVIDER_FACTOR
        val field: Field
        try {
            field = TabLayout::class.java.getDeclaredField(
                SCROLLABLE_TAB_MIN_WIDTH
            )
            field.isAccessible = true
            field.set(this, tabMinWidth)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}

class Utils{

    private val WIDTH_INDEX = 0
    private val HEIGHT_INDEX = 1

    fun getScreenSize(context: Context): IntArray {
        val widthHeight = IntArray(2)
        widthHeight[WIDTH_INDEX] = 0
        widthHeight[HEIGHT_INDEX] = 0
        val windowManager: WindowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        widthHeight[WIDTH_INDEX] = size.x
        widthHeight[HEIGHT_INDEX] = size.y
        if (!isScreenSizeRetrieved(widthHeight)) {
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            widthHeight[0] = metrics.widthPixels
            widthHeight[1] = metrics.heightPixels
        }

        // Last defense. Use deprecated API that was introduced in lower than API 13
        if (!isScreenSizeRetrieved(widthHeight)) {
            widthHeight[0] = display.width // deprecated
            widthHeight[1] = display.height // deprecated
        }
        return widthHeight
    }

    private fun isScreenSizeRetrieved(widthHeight: IntArray): Boolean {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0
    }
}