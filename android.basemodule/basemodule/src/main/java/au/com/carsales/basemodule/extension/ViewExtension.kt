package au.com.carsales.basemodule.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import androidx.annotation.FontRes
import androidx.fragment.app.Fragment
import androidx.core.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.getShortEdgePixels(context: Context?): Int? {
    var metrics = context?.resources?.displayMetrics
    return metrics?.run { if (widthPixels > heightPixels) heightPixels else widthPixels }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.disableView() {
    this.isEnabled = false
    this.alpha = 0.5f
}

fun View.enableView() {
    this.isEnabled = true
    this.alpha = 1.0f
}

fun View.disableAndPostDelayEnabled() {
    this.isEnabled = false
    Handler().postDelayed({
        this.isEnabled = true
    }, 1500)
}

fun View.showKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 1)
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.hideKeyboard() {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
        imm.hideSoftInputFromInputMethod(this.windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.setCustomShapeBackground(backgroundColor: Int?, borderColor: Int?) {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadius = 80f
    if (backgroundColor != null) {
        shape.setColor(backgroundColor)
    }

    if (borderColor != null) {
        shape.setStroke(1, borderColor)
    }

    this.background = shape
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)


fun TextView.setErrorText(text: String?) {

    val value = text ?: String.empty()
    if (!value.contains("Unable to resolve host") &&
            !value.contains("Failed to connect to") &&
            !value.contains("Trust anchor for")) {
        this.text = value
    }
}

fun TextView.setFontType(@FontRes fontRes: Int) {

    try {
        val typeface = ResourcesCompat.getFont(this.context, fontRes)
        if (typeface != null) {
            setTypeface(typeface)
        }
    } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
    }

}

fun LinearLayout.setHeight(height: Int) {
    val params = this.layoutParams
    params.height = height
    this.layoutParams = params
}

@Deprecated("new version: getScreenWidthPixels in BaseUtils.kt, without the need of context")
fun View.getDisplayWidthPixels(): Int {
    var metrics = this.resources.getDisplayMetrics() ?: null
    return metrics?.widthPixels ?: 0
}
