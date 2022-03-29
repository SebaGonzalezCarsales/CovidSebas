package au.com.carsales.basemodule.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentManager
import androidx.core.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.toast
import au.com.carsales.basemodule.widget.cardview.CSCardView
import au.com.carsales.basemodule.widget.cardview.interfaces.ListItem
import java.io.*
import java.io.Closeable
import java.io.IOException
import java.io.OutputStream


const val HEADER_TYPE = 0
const val DATA_TYPE = 1

fun isTablet(context: Context): Boolean = try {
    context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}

fun isPhone(context: Context): Boolean = try {
    !context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}


fun isPortrait(context: Context): Boolean = try {
    context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
} catch (ex: Exception) {
    false
}

fun launchUrlInCustomTabBase(activity: Activity, url: String) {

    try {
        val chrome = arrayOf("com.android.chrome",
                "com.chrome.beta",
                "com.chrome.dev",
                "com.google.android.apps.chrome")

        var isChromeInstall = false
        chrome.forEach {
            try {
                activity.packageManager.getPackageInfo(it, 0)
                isChromeInstall = true
                return@forEach
            } catch (nameNotFoundException: PackageManager.NameNotFoundException) {
            }
        }
        if (!isChromeInstall) {
//            activity.launchUrlWebView(url)
            return
        }

        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(ContextCompat.getColor(activity, R.color.primaryColor))
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(activity, R.color.primaryColor))

        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))

    } catch (exception: Exception) {
        activity.toast(activity.getString(R.string.generic_error_message))
    }
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    val resource = context.resources
    val displayMetrics = resource.displayMetrics
    return dp * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


fun ConstraintLayout.setOnBindCSCardViewBorders(viewType: Int) {
    val csCardView: CSCardView = this as CSCardView
    when (viewType) {
        ListItem.TYPE_ANSWER_TOP -> csCardView.showCorner(true, true, false, false)
        ListItem.TYPE_ANSWER_MIDDLE -> csCardView.showCorner(false, false, false,
                false)
        ListItem.TYPE_ANSWER_BOTTOM -> csCardView.showCorner(false, false, true, true)
        ListItem.TYPE_ANSWER_SINGLE -> {
        }
    }
}

fun View.setMarginForLastAdapterPosition(adapterPosition: Int, size: Int, context: Context) {
    if (adapterPosition == size - 1) {

        val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(
                convertDpToPixel(20f, context).toInt(),
                0,
                convertDpToPixel(22f, context).toInt(),
                convertDpToPixel(70f, context).toInt()
        )
        this.layoutParams = layoutParams
    }
}

fun Context.getImageUri(mImageCaptureUri: Uri): Bitmap? {

    return try {

        var bitmapFile = MediaStore.Images.Media.getBitmap(this.contentResolver, mImageCaptureUri)
        if (bitmapFile == null) return null
        bitmapFile = ImageUtils(this).setDefaultRotatePhoto(bitmapFile!!, mImageCaptureUri.path!!, mImageCaptureUri)
        bitmapFile = saveBitmapToFile(this, bitmapFile, mImageCaptureUri)
        return bitmapFile
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun saveBitmapToFile(context: Context, croppedImage: Bitmap, saveUri: Uri?): Bitmap? {
    if (saveUri != null) {
        var outputStream: OutputStream? = null
        try {
            outputStream = context.contentResolver.openOutputStream(saveUri)
            if (outputStream != null) {
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
        } catch (e: IOException) {

        } finally {
            closeSilently(outputStream)
            return croppedImage
        }
    }

    return null
}

private fun closeSilently(@Nullable c: Closeable?) {
    if (c == null) {
        return
    }
    try {
        c.close()
    } catch (t: Throwable) {
        // Do nothing
    }

}

inline fun guard(invertCondition: Boolean, body: () -> Unit) {
    if (!invertCondition)
        body()
}

fun <T> unwrap(vararg optionals: T?): Chainer<T> {
    optionals.forEach {
        if (it == null) {
            return Chainer(null)
        }
    }
    return Chainer(optionals.map { it!! })
}

class Chainer<out T>(val unwrapped: List<T>?) {
    inline infix fun <R> or(block: () -> R): List<T> {
        if (unwrapped == null) {
            block() // should halt execution before throw -- block is inlined
            throw RuntimeException("Please return in otherwise block")
        } else {
            return unwrapped
        }
    }
}


fun dpToPx(dp: Float): Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)).toInt()

fun dpToPx(dp: Int) = dpToPx(dp.toFloat())

fun spToPx(sp: Int) = spToPx(sp.toFloat())

fun spToPx(sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem()?.displayMetrics).toInt()

fun pxToDP(pixels: Int) = Math.floor((pixels / (Resources.getSystem().displayMetrics.densityDpi / 160f)).toDouble()).toInt()


fun <T : Serializable> deepCopy(obj: T?): T? {
    if (obj == null) return null
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(obj)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}


fun getScreenWidthPixels(): Int {
    var metrics = Resources.getSystem().displayMetrics
    return metrics.widthPixels
}


fun getScreenHeightPixels(): Int {
    var metrics = Resources.getSystem().displayMetrics
    return metrics.heightPixels
}


// Check if state saved and safe commit
fun FragmentManager?.checkSafeCommit(onCommitUnit: (() -> Unit)? = null) {
    try {
        this?.let {
            if (!isStateSaved) {
                onCommitUnit?.invoke()
            }
        }
    } catch (e: IllegalStateException) {
        e.printStackTrace()

        /*Crashlytics.log(
                Log.ERROR, "fragment: BaseNavigationHelper"
                , "BaseNavigationHelper - replaceRootFragment "
        )*/

        // Send "immediately the report to Crashlytics
        //Crashlytics.logException(e)
    }
}

fun FragmentManager?.checkSafeRemoveFragment(onCommitUnit: (() -> Unit)? = null) {
    try {
        this?.let {
            if (!isDestroyed) {
                onCommitUnit?.invoke()
            }
        }
    } catch (e: IllegalStateException) {
        e.printStackTrace()

        /*Crashlytics.log(
                Log.ERROR, "fragment: BaseNavigationHelper"
                , "BaseNavigationHelper - replaceRootFragment "
        )*/

        // Send "immediately the report to Crashlytics
        //Crashlytics.logException(e)
    }
}

inline fun <reified T : Any> Any.getThroughReflection(propertyName: String): T? {
    val getterName = "get" + propertyName.capitalize()
    return try {
        javaClass.getMethod(getterName).invoke(this) as? T
    } catch (e: NoSuchMethodException) {
        null
    }
}