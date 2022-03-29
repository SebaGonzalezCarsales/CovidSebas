package au.com.carsales.basemodule.extension

import android.app.Activity
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.util.TypedValue
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import au.com.carsales.basemodule.R

fun Toolbar.changeColor(color: Int) {
    val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY)
    for (i in 0..this.childCount) {
        val viewToolbar = this.getChildAt(i)
        if (viewToolbar is ImageButton) {
            (viewToolbar).drawable.colorFilter = colorFilter
        }
        if (viewToolbar is ActionMenuItemView) {
            val drawablesCount = viewToolbar.compoundDrawables.size

            for (y in 0..drawablesCount) {
                if (viewToolbar.compoundDrawables[y] != null) {

                }
                viewToolbar.post { (viewToolbar).compoundDrawables[y].colorFilter = colorFilter }
            }
        }
    }
}

fun Toolbar.applyFontForToolbarTitle(context: Activity, color: Int = R.color.primaryColor) {
    for (i in 0 until this.childCount) {
        val view = this.getChildAt(i)
        if (view is TextView) {
            try {
                val titleFont = ResourcesCompat.getFont(context, R.font.opensans_regular)
                val subtitleFont = ResourcesCompat.getFont(context, R.font.opensans_regular)
                if (view.text == this.title) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
                    view.setTextColor(resources.getColor(color))
                    view.typeface = titleFont
                } else if (view.text == this.subtitle) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f)
                    view.setTextColor(resources.getColor(color))
                    view.typeface = subtitleFont
                    break
                }
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                //Use crashlytics to get more info of the crash
                //Crashlytics.log(Log.ERROR, "ToolbarExtension", " Toolbar - applyFontForToolbarTitle: " + e.printStackTrace().toString())
            }
        }
    }
    (context as AppCompatActivity).setSupportActionBar(this)
}

fun Toolbar.setArrowUpToolbar(context: Activity) {
    if (context is AppCompatActivity) {
        context.setSupportActionBar(this)
        context.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}

fun Toolbar.setNoArrowUpToolbar(context: Activity) {
    if (context is AppCompatActivity) {
        context.setSupportActionBar(this)
        context.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}