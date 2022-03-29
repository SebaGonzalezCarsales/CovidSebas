package au.com.carsales.basemodule.util

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import au.com.carsales.basemodule.context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet


class VarColumnGridLayoutManager : androidx.recyclerview.widget.GridLayoutManager {

    companion object {
        private const val FAKE_COUNT = 3
    }


    @Nullable
    private var columnCountProvider: ColumnCountProvider? = null

    interface ColumnCountProvider {
        fun getColumnCount(recyclerViewWidth: Int): Int
    }

    class DefaultColumnCountProvider(@param:NonNull @field:NonNull
                                     private val context: Context) : ColumnCountProvider {

        override fun getColumnCount(recyclerViewWidth: Int): Int {
            return columnsForWidth(recyclerViewWidth)
        }

        companion object {

            fun columnsForWidth(widthPx: Int): Int {
                val widthDp = dpFromPx(widthPx.toFloat())
                return when {
                    widthDp >= 900 -> 5
                    widthDp >= 720 -> 5
                    widthDp >= 600 -> 5
                    widthDp >= 480 -> 5
                    widthDp >= 320 -> 4
                    else -> 3
                }
            }

            private fun dpFromPx(px: Float): Int {
                return (px / context!!.resources.displayMetrics.density + 0.5f).toInt()
            }
        }
    }


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context): super(context, FAKE_COUNT)


    constructor(context: Context, orientation: Int, reverseLayout: Boolean): super(context, FAKE_COUNT, orientation, reverseLayout)

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler?,
                                  state: androidx.recyclerview.widget.RecyclerView.State) {
        updateSpanCount(width)
        super.onLayoutChildren(recycler, state)
    }

    private fun updateSpanCount(width: Int) {
        if (columnCountProvider != null) {
            val spanCount = columnCountProvider!!.getColumnCount(width)
            setSpanCount(if (spanCount > 0) spanCount else 1)
        }
    }

    fun setColumnCountProvider(provider: ColumnCountProvider?) {
        this.columnCountProvider = provider
    }
}