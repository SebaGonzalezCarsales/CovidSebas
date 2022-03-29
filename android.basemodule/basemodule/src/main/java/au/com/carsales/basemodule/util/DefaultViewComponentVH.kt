package au.com.carsales.basemodule.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Matias Arancibia on 24 Mar, 2020
 * Copyright (c) 2020 Carsales. All rights reserved.
 */

/**
 * This Default ViewHolder is useful when we handle a list of items with a specific type and
 * one or more elements doesn't match with any known item type, then we show this "empty" view
 * on the RecyclerView
 * */
class DefaultViewComponentVH(itemView: View) : RecyclerView.ViewHolder(itemView)