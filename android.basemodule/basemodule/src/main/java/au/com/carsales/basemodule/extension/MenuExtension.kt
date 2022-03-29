package au.com.carsales.basemodule.extension

import android.graphics.PorterDuff
import android.os.Handler
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem

fun MenuItem.disableAndPostDelayEnabled(delayMillis: Long = 1500) {
    this.isEnabled = false
    Handler().postDelayed({
        this.isEnabled = true
    }, delayMillis)
}

fun MenuItem.tint(color: Int) {
    val drawable = this.icon
    if (drawable != null) {
        // If we don't mutate the drawable, then all drawable's with this id will have a color
        // filter applied to it.
        drawable.mutate()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}

fun MenuItem.tintText(color: Int) {
    val s = SpannableString(title)
    s.setSpan(ForegroundColorSpan(color), 0, s.length, 0)
    
    title = s
}

