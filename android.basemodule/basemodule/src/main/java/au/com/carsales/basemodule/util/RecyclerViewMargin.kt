package au.com.carsales.basemodule.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

class RecyclerViewMargin(private var spacing: Int = 0, private var spanCount: Int = 0, private var includeEdge: Boolean, private var headerNum: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        val column: Int
        val position: Int
        val lp = view.layoutParams
        if (lp is GridLayoutManager.LayoutParams) {
            position = parent.getChildAdapterPosition(view)
            //override the values
            column = lp.spanIndex
            spanCount = (parent.layoutManager as GridLayoutManager).spanCount

        } else {
            //use the values in params to calculate
            position = parent.getChildAdapterPosition(view) - headerNum // item position
            column = position % spanCount // item column
        }


        if (position >= 0) {

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }

    }
}