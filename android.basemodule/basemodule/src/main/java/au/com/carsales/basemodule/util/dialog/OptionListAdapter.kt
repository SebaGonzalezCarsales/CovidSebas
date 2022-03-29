package au.com.carsales.basemodule.util.dialog

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.inflate

class OptionListAdapter constructor(private val items: ArrayList<String>, private val optionPreSelected: Int)
    : androidx.recyclerview.widget.RecyclerView.Adapter<OptionListAdapter.ViewHolder>() {

    private var lastRadioCheckedRadioButton: RadioButton? = null

    var actionListener: OptionListAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.content_dialog_item_list_with_radio_button))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val option = items[position]

        holder.itemListTextView.text = option

        if (position == optionPreSelected) {
            lastRadioCheckedRadioButton = holder.itemListRadioButton
            holder.itemListRadioButton.isChecked = true
        }
        holder.itemView.setOnClickListener {
            lastRadioCheckedRadioButton?.isChecked = false
            lastRadioCheckedRadioButton = holder.itemListRadioButton
            holder.itemListRadioButton.isChecked = true
            actionListener?.onItemPressed(option)
        }
    }

    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val itemListTextView: TextView = view.findViewById(R.id.itemListTextView)
        val itemListRadioButton: RadioButton = view.findViewById(R.id.itemListRadioButton)
    }

    interface OptionListAdapterListener {
        fun onItemPressed(item: String)
    }
}

