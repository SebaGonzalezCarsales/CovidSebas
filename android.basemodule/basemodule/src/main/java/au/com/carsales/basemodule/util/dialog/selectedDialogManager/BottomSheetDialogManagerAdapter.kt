package au.com.carsales.basemodule.util.dialog.selectedDialogManager

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.gone
import au.com.carsales.basemodule.extension.inflate
import au.com.carsales.basemodule.extension.visible

class BottomSheetDialogManagerAdapter<T>(typeOptionManager: TypeBottomSheetDialogSelected,
                                         private val itemsList: List<OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>>,
                                         optionSheetAdapterListener: OptionSheetAdapterListener?) : OptionBottomSheetAdapterBase<T>(typeOptionManager,
        optionSheetAdapterListener) {

    override var items: List<OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>> = itemsList
        get() = itemsList


    override fun createItemViewHolder(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder? {
        return ViewHolder(parent.inflate(R.layout.content_dialog_item_list_without_radio_button))
    }

    override fun bindItemViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int,
                                    data: OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>) {

        (viewHolder as BottomSheetDialogManagerAdapter<*>.ViewHolder).itemListTextView.text = data.title

        when (typeOption) {

            TypeBottomSheetDialogSelected.SINGLE_SELECTION_WITH_CHECK,
            TypeBottomSheetDialogSelected.MULTIPLE_SELECTION_WITH_CHECK -> {
                viewHolder.itemView.isEnabled = true
                if (data.isSelected) {
                    viewHolder.itemCheckImageView.visible()
                } else {
                    viewHolder.itemCheckImageView.gone()
                }
            }
            else -> {
                viewHolder.itemCheckImageView.gone()
                viewHolder.itemView.isEnabled = false
            }
        }

        viewHolder.containerCheckView.setOnClickListener {
            changeOnClickForType(position)
        }
    }


    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val itemListTextView: TextView = view.findViewById(R.id.itemListTextView)
        val itemCheckImageView: ImageView = view.findViewById(R.id.itemCheckImageView)
        val containerCheckView: RelativeLayout = view.findViewById(R.id.containerCheckView)
    }
}