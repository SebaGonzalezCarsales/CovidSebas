package au.com.carsales.basemodule.widget.adapter.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import au.com.carsales.basemodule.BR


/**
 * Created by Dan on 07, June, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
class BaseBindViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : T, clickHandler: BaseBindClickHandler<T>? = null) {

        binding.setVariable(BR.item, item)

        if(clickHandler != null)
            binding.setVariable(BR.clickHandler, clickHandler)

        binding.executePendingBindings()
    }
}