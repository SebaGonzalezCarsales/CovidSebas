package au.com.carsales.basemodule.widget.error

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import au.com.carsales.basemodule.R
import kotlinx.android.synthetic.main.view_error.view.*

/**
 * Created by joseignacio on 2/9/18.
 */
class  ErrorView : LinearLayout {

    var errorListener: ErrorViewListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        errorViewTryAgainButton.setOnClickListener { errorListener?.onTryAgainClicked() }
        errorViewSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            context.startActivity(intent)
        }
//        errorViewSettingsButton.setOnClickListener { errorListener?.onSettingsClicked() }
    }
}