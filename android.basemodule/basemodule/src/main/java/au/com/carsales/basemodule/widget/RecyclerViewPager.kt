package au.com.carsales.basemodule.widget


import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by kelly.wang on 25/09/2015.
 */
class RecyclerViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) : RecyclerView(context, attrs, defStyle) {
    var isPagerEnabled = true
    private var selectedItemPosition = 0

    private var mOnPageChangeListeners: MutableList<androidx.viewpager.widget.ViewPager.OnPageChangeListener>? = null

    private val itemCount: Int
        get() = if (this.adapter == null) 0 else this.adapter!!.itemCount


    init {
        if (!isInEditMode) {
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {

        if (isPagerEnabled && layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            val linearLayoutManager = layoutManager as androidx.recyclerview.widget.LinearLayoutManager

            //int lastVisibleItemPos = linearLayoutManager.findLastVisibleItemPosition();
            //int firstVisibleItemPos = linearLayoutManager.findFirstVisibleItemPosition();

            val firstVisibleItemPos = Math.max(
                selectedItemPosition - 1,
                linearLayoutManager.findFirstVisibleItemPosition()
            )
            val lastVisibleItemPos = Math.min(
                selectedItemPosition + 1,
                linearLayoutManager.findLastVisibleItemPosition()
            )

            val firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPos)
            val lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPos)
            if (firstView == null || lastView == null) {
                return true
            }
            val recyclerViewWidth = this.measuredWidth
            //these variables get the distance you need to scroll in order to center your views.
            //my views have variable sizes, so I need to calculate side margins separately.
            //note the subtle difference in how right and left margins are calculated, as well as
            //the resulting scroll distances.
            val leftMargin = (recyclerViewWidth - lastView.width) / 2
            val rightMargin = (recyclerViewWidth - firstView.width) / 2 + firstView.width
            val leftEdge = lastView.left
            val rightEdge = firstView.right
            val scrollDistanceLeft = leftEdge - leftMargin
            val scrollDistanceRight = rightMargin - rightEdge
            if (velocityX > 0) {
                //swipes to left
                smoothScrollBy(scrollDistanceLeft, 0)
                selectedItemPosition = lastVisibleItemPos
                dispatchOnPageSelected(selectedItemPosition)

            } else {
                smoothScrollBy(-scrollDistanceRight, 0)
                selectedItemPosition = firstVisibleItemPos
                dispatchOnPageSelected(selectedItemPosition)
            }

            return true
        } else {
            return super.fling(velocityX, velocityY)
        }
    }

    fun addOnPageChangeListener(listener: androidx.viewpager.widget.ViewPager.OnPageChangeListener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = ArrayList<androidx.viewpager.widget.ViewPager.OnPageChangeListener>()
        }
        mOnPageChangeListeners!!.add(listener)
    }

    fun removeOnPageChangeListener(listener: androidx.viewpager.widget.ViewPager.OnPageChangeListener) {
        mOnPageChangeListeners?.remove(listener)

    }

    /**
     * Remove all listeners that are notified of any changes in scroll state or position.
     */
    fun clearOnPageChangeListeners() {
        mOnPageChangeListeners?.clear()
    }


    fun getSelectedItemPosition(): Int {
        return selectedItemPosition
    }

    private fun dispatchOnPageSelected(position: Int) {
        mOnPageChangeListeners?.let {
            it.forEach { entry ->
                entry.onPageSelected(position)
            }
        }
    }


}
