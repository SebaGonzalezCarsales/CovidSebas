package au.com.carsales.basemodule.util.layoutManager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class WrapContentGridLayoutManager(context: Context?, spanCount: Int) :
        androidx.recyclerview.widget.GridLayoutManager(context, spanCount) {

    override fun onLayoutChildren(recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens")
        } catch (e: IllegalArgumentException) {
            Log.e("Error", "IllegalArgumentException in RecyclerView happens")
        }
    }
}