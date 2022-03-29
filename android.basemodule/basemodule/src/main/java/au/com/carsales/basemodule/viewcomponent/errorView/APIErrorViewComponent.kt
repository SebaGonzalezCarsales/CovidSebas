package au.com.carsales.basemodule.viewcomponent.errorView

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.databinding.ViewErrorGlobalBinding

/**
 * Created by Dan on 31, May, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * Used to show api error
 * response through all apps
 * By default, the close and
 * try again button is in GONE
 * visibility. Both have to be
 * set to true ( in corresponding
 * methods) to set visibility to VISIBLE
 *
 * Visibility of these buttons can
 * be set also in xml (app:showTryAgainBtn="false")
 * "canRefresh" field from API will override
 * this previously mentioned values
 */
class APIErrorViewComponent : LinearLayout {

    private lateinit var binding: ViewErrorGlobalBinding

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        binding = ViewErrorGlobalBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setErrorClickHandler(errorClickHandler: GlobalErrorClickHandler?) {

        val errorHandler: GlobalErrorClickHandler = object : GlobalErrorClickHandler {
            override fun onCloseClick(view: View, error: APIErrorViewData) {
                errorClickHandler?.onCloseClick(view, error)
            }

            override fun onSettingsClick(view: View, error: APIErrorViewData) {
                val intent = Intent(Settings.ACTION_SETTINGS)
                view.context.startActivity(intent)
            }

            override fun onTryAgainClick(view: View, error: APIErrorViewData) {
                errorClickHandler?.onTryAgainClick(view, error)
            }
        }

        binding.clickHandler = errorHandler
    }

    fun setData(apiErrorViewData: APIErrorViewData?) {
        apiErrorViewData?.let {
            binding.error = it
        }

    }

    fun setBackground(background: Int?) {
        background?.let { binding.background = it  }
    }
}

@BindingAdapter("errorBg")
fun View.setErrorBg(background: Int?) {
    if (background != null && background != 0) {
        setBackgroundColor(background)
    } else
        setBackgroundColor(ContextCompat.getColor(context, R.color.lighter_medium_gray))

}