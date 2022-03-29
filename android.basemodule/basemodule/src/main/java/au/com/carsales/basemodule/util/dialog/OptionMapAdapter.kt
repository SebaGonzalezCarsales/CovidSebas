package au.com.carsales.basemodule.util.dialog

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.inflate

open class OptionMapAdapter constructor(private val items: Map<String, Any>) : androidx.recyclerview.widget.RecyclerView.Adapter<OptionMapAdapter.ViewHolder>() {

    var actionListener: OptionMapAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionMapAdapter.ViewHolder =
            ViewHolder(parent.inflate(R.layout.content_dialog_item_list_without_radio_button))

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: OptionMapAdapter.ViewHolder, position: Int) {

        val key = items.keys.toTypedArray()[position]
        holder.itemListTextView.text = key

        holder.itemView.setOnClickListener {
            actionListener?.onItemSelected(items[key]!!)
        }
    }

    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val itemListTextView: TextView = view.findViewById(R.id.itemListTextView)
    }

    interface OptionMapAdapterListener {
        fun onItemSelected(item: Any)
    }
}

