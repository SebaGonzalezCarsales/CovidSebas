package au.com.carsales.basemodule.viewcomponent.errorView

import android.view.View
import au.com.carsales.basemodule.BaseModuleDialogFragment
import au.com.carsales.basemodule.BaseModuleFragment

/**
 * Created by Dan on 24, June, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * All methods are optional.
 */
interface GlobalErrorClickHandler {

    fun onCloseClick(view: View, error: APIErrorViewData){}

    fun onSettingsClick(view: View, error: APIErrorViewData){}

    fun onTryAgainClick(view: View, error: APIErrorViewData){}
}