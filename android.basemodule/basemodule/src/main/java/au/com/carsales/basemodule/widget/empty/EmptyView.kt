package au.com.carsales.basemodule.widget.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.toast
import au.com.carsales.basemodule.util.guard
import kotlinx.android.synthetic.main.view_empty.view.*

/**
 * Created by joseignacio on 2/9/18.
 */
class EmptyView : LinearLayout {

    var emptyListener: EmptyViewListener? = null

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
        LayoutInflater.from(context).inflate(R.layout.view_empty, this)

        emptyViewTryAgainButton.setOnClickListener { emptyListener?.onCheckAgainClicked() }
    }
}