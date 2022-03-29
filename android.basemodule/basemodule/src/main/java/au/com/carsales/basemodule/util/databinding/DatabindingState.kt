package au.com.carsales.basemodule.util.databinding

import androidx.databinding.ObservableBoolean

/**
 * Created by Dan on 23, March, 2020
 * Copyright (c) 2020 Carsales. All rights reserved.
 *
 * Used to manage view states with databinding from
 * module to another module
 */
class DatabindingState {
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)
    var isEmpty: ObservableBoolean = ObservableBoolean(false)
    var isRefreshing: ObservableBoolean = ObservableBoolean(false)

    //This observable boolean is to prevent showing
    // the main XML before data arrives (mainly used for this purpose)
    var hasContent: ObservableBoolean = ObservableBoolean(false)


}