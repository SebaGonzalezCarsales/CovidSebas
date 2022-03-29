package au.com.carsales.basemodule.extension

import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.com.carsales.basemodule.R


fun Context.intentWebLink(url: String) =
        try {
            val i = Intent(Intent.ACTION_VIEW)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.data = Uri.parse(url)
            this.startActivity(i)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            this.toast(this.getString(R.string.can_not_open_link), Toast.LENGTH_SHORT)
        }

@SuppressLint("ShowToast")
fun Context.getToastTypefaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)

    var toastTV: TextView? = null
    if (toast.view is RelativeLayout) {
        val toastLayout = toast.view as RelativeLayout
        toastTV = (toastLayout?.getChildAt(0) as? TextView)
    } else if (toast.view is LinearLayout) {
        val toastLayout = toast.view as LinearLayout
        toastTV = (toastLayout?.getChildAt(0) as? TextView)
    }
    toastTV?.typeface = typeface
    return toast
}

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
        text?.let {
            getToastTypefaced(text, duration).show()
        }


fun Context.toastBottomRectangle(text: String?, duration: Int = Toast.LENGTH_SHORT) =
        text?.let {
            val inflate = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val layout = inflate.inflate(R.layout.toast_rectangle_layout, null)
            val tv = layout.findViewById(R.id.toast_message) as TextView
            tv.text = it

            val toast = Toast(applicationContext)
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.duration = duration
            toast.view = layout
            toast.show()

        }


fun Context.initSwipe(swipe: SwipeRefreshLayout) {
    swipe.setColorSchemeColors(
            ContextCompat.getColor(this, R.color.primaryColor),
            ContextCompat.getColor(this, R.color.primaryDarkColor))
//        swipe.setOnRefreshListener({ mSwipeCallback.onSwipe() })
}

fun Context.clipboard(value: String, successText: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied TextDataView", value)
    clipboard.setPrimaryClip(clip)
    this.toast(successText)
}

fun Context.canMakeCallsViaTelephonyNetwork(): Boolean {
    val telephonyService = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
            && telephonyService != null
            && telephonyService.networkType != TelephonyManager.NETWORK_TYPE_UNKNOWN)
}

fun Context.canMakeCalls(): Boolean {
    val callIntent = Intent(Intent.ACTION_DIAL)
    return (callIntent.resolveActivity(this.packageManager) != null)
}


@Deprecated("use the one in BaseUtils, which doesn't need context")
fun Context.spToPx(sp: Int) = spToPx(sp.toFloat())

@Deprecated("use the one in BaseUtils, which doesn't need context")
fun Context.spToPx(sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)

@Deprecated("use the one in BaseUtils, which doesn't need context")
fun Context.dpToPx(dp: Int) = {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.getResources().getDisplayMetrics())
    px.toInt()
}

@Deprecated("use the one in BaseUtils, which doesn't need context")
fun Context.pxToDP(pixels: Int) = {
    val metrics = this.getResources().getDisplayMetrics()
    Math.floor((pixels / (metrics.densityDpi / 160f)).toDouble()).toInt()
}


fun Context.getApplicationName(): String {
    val applicationInfo = applicationInfo
    val stringId = applicationInfo?.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else getString(stringId!!)
}




