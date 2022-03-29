package au.com.carsales.basemodule.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.view.Surface
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.util.isTablet
import java.util.*

fun Activity.getShortEdgePixels(): Int {
    var metrics = getResources().getDisplayMetrics()
    return metrics.run { if (widthPixels > heightPixels) heightPixels else widthPixels }
}

fun Activity.getWidthPixels(): Int {
    var metrics = getResources().getDisplayMetrics()
    return metrics.widthPixels
}

fun Activity.hideSoftKeyboard() {
    try {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            inputMethodManager.hideSoftInputFromInputMethod(currentFocus!!.windowToken, 0)
        }
    } catch (e: NullPointerException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@SuppressLint("ShowToast")
fun Activity.getToastTypeFaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)
    val toastLayout = toast.view as? LinearLayout
    val toastTV = (toastLayout?.getChildAt(0) as? TextView)
    toastTV?.typeface = typeface
    return toast
}

fun Activity.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
        text?.let {
            getToastTypeFaced(text, duration).show()
        }

fun Activity.getPackageInfo(): PackageInfo =
        this.packageManager.getPackageInfo(this.packageName, 0)

fun Activity.isLargeTypeDevice(): Boolean {
    return this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun Activity.isExtraLargeTypeDevice(): Boolean {
    return this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE
}

fun Activity.isXLargeTypeDevice(): Boolean {
    return this.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE
}

fun Activity.isPortraitOrientationDevice(): Boolean {
    val rotation = (Objects.requireNonNull(this.getSystemService(Context.WINDOW_SERVICE)) as WindowManager)
            .defaultDisplay.orientation
    return when (rotation) {
        Surface.ROTATION_0 -> true
        Surface.ROTATION_90 -> false
        Surface.ROTATION_180 -> true
        Surface.ROTATION_270 -> false
        else -> false
    }
}

fun Activity.configurationRotateDevice(excludeThisActivity: Activity? = null, body: () -> Unit = {}) {
    when (this) {
        excludeThisActivity -> {
            body()
        }
        else -> {
            requestedOrientation = if (isTablet(this)) {
                ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
}

fun Activity.setTextErrorText(text: String): String {
    var newText = text
    if (text.contains("Unable to resolve host") &&
            !text.contains("Failed to connect to") &&
            !text.contains("Trust anchor for")) {
        newText = this.getString(R.string.generic_no_connection_message)
    }
    return newText
}