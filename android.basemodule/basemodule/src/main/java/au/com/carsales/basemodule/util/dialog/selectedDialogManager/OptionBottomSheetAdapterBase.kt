package au.com.carsales.basemodule.util.dialog.selectedDialogManager

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import java.io.Serializable

abstract class OptionBottomSheetAdapterBase<T> constructor(val typeOption: TypeBottomSheetDialogSelected,
                                                           private val optionSheetAdapterListener: OptionSheetAdapterListener?) :
        androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    abstract var items: List<OptionsBottomSheetData<T>>

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder? = null
        return createItemViewHolder(parent)!!
    }

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        bindItemViewHolder(viewHolder, position)
    }

    protected abstract fun createItemViewHolder(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder?
    protected abstract fun bindItemViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                                              position: Int,
                                              data: OptionsBottomSheetData<T> = items[position])

    fun changeOnClickForType(position: Int) {

        when (typeOption) {
            TypeBottomSheetDialogSelected.SINGLE_SELECTION_WITH_CHECK -> {
                singleSelectionNotify(position)
            }
            TypeBottomSheetDialogSelected.MULTIPLE_SELECTION_WITH_CHECK -> {
                multipleSelectionNotify(position)
            }
        }

    }

    open fun multipleSelectionNotify(position: Int) {
        items[position].isSelected = !items[position].isSelected
        notifyItemChanged(position)
        optionSheetAdapterListener?.changeMultipleAdapterData(items.any { it.isSelected })

    }

    open fun singleSelectionNotify(position: Int) {

        items.forEachIndexed { index, data ->
            data.isSelected = index == position
        }
        notifyDataSetChanged()
        optionSheetAdapterListener?.changeSingleAdapterData()
    }


    class OptionsBottomSheetData<T>(
            val title: String,
            var dataOption: T,
            var isSelected: Boolean) : Serializable
}

interface OptionSheetAdapterListener {
    fun changeSingleAdapterData()
    fun changeMultipleAdapterData(isEnable: Boolean)
}