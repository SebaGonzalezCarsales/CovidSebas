package au.com.carsales.basemodule.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.ConnectivityManager
import android.net.Uri
import androidx.annotation.StringDef
import android.util.Log
import android.view.View
import android.widget.ImageView
import au.com.carsales.basemodule.GlideApp
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.getAppContext
import au.com.carsales.basemodule.util.glide.svg.SvgSoftwareLayerSetter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


/**
 * scales (up and down) the image to completely fill the size specified, removing any overhang.
 * */
const val METHOD_CROP = "crop"
/**
 * scales (up and down) the image to fit within the size specified, leaving any additional space unfilled.
 */
const val METHOD_FIT = "fit"
/**
 * scales (up and down) the image to fit within the size specified, filling any additional space with the background colour.
 */
const val METHOD_FITFILL = "fitfill"
/**
 * scales (down ONLY)  the image to fit within the size specified, leaving any additional space unfilled.
 */
const val METHOD_LIMIT = "limit"
/**
 * scales (down ONLY) the image to fit within the size specified, filling any additional space with the background colour.
 */
const val METHOD_LIMIT_FILL = "limitfill"

const val FORMAT_AUTO = "auto" //encodes to webp where supported, otherwise jpeg
const val FORMAT_WEBP = "webp"
const val FORMAT_JPEG = "jpeg"

@StringDef(METHOD_CROP, METHOD_FIT, METHOD_FITFILL, METHOD_LIMIT, METHOD_LIMIT_FILL)
@Retention(AnnotationRetention.SOURCE)
annotation class MethodType

@StringDef(FORMAT_AUTO, FORMAT_WEBP, FORMAT_JPEG)
@Retention(AnnotationRetention.SOURCE)
annotation class FormatType

fun View.getThumbnailWidth(): Int {
    return dpToPx(layoutParams.width)
}

fun Context.getViewWidthPixels(): Int {

    val metrics = this.resources.displayMetrics
    return metrics.widthPixels
}

fun Context.getSizePixels(): Pair<Int, Int> {
    val metrics = this.resources.displayMetrics
    return Pair(metrics.widthPixels, metrics.heightPixels)
}

fun Context.pxCrushDensity(): Int {
    val scale = this.resources.displayMetrics.density
    return if (scale > 1.0) {
        //Is high density
        2
    } else {
        1
    }
}

/*
* this requirement starts with my account api
* but it will gradually rollout to other membershi api and retail xapi.
* */

fun Context.xApiIconDensity(): Int {
    val scale = this.resources.displayMetrics.density
    return if (scale > 2.0) {
        3
    } else if (scale > 1.0) {
        2
    } else
        1
}


/*
* density:
* it is used to show size of watermark, according to ui observation, 1 is
* always good in all cases for android
* */
fun generateImageUrl(url: String?, width: Int, height: Int,
                     density: Int? = 1, @MethodType method: String = METHOD_CROP,
                     @FormatType format: String = FORMAT_AUTO): String {
    val uriBuilder = Uri.parse(url ?: "").buildUpon()
    if (width > 0) {
        uriBuilder.appendQueryParameter("pxc_width", width.toString())
    }
    if (height > 0) {
        uriBuilder.appendQueryParameter("pxc_height", height.toString())
    }

    density?.let {
        uriBuilder.appendQueryParameter("pxc_density", density.toString())
    }

    uriBuilder.appendQueryParameter("pxc_method", method)
    uriBuilder.appendQueryParameter("pxc_format", format)

    return uriBuilder.toString()
}

fun ImageView.loadImageWithPlaceholder(url: String) =
        GlideApp.with(getAppContext())
                .load(url)
                .placeholder(R.drawable.ic_base_no_image)
                .error(R.drawable.ic_base_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)


fun ImageView.loadImageWithoutPlaceholder(url: String) =
        GlideApp.with(getAppContext())
                .load(url)
                .error(R.drawable.ic_base_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)


fun ImageView.loadImageWithCache(url: String) =
        GlideApp.with(getAppContext())
                .load(url)
                .onlyRetrieveFromCache(false)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun Context.loadImageWithCallback(url: String, width: Int = 0, heigth: Int = 0, imageCallback: DownloadImageCallback) {

    var simpleTarget = if (width == 0 || heigth == 0) {
        object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageCallback.downloadComplete(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                imageCallback.downFail(convertDrawableToBitmap(resources.getDrawable(R.drawable.ic_base_no_image)))
            }

        }
    } else {
        object : SimpleTarget<Bitmap>(width, heigth) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageCallback.downloadComplete(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                imageCallback.downFail(convertDrawableToBitmap(resources.getDrawable(R.drawable.ic_base_no_image)))
            }

        }
    }

    GlideApp.with(this)
            .asBitmap()
            .load(url)
            .into(simpleTarget)
}


private fun convertDrawableToBitmap(drawable: Drawable): Bitmap {

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888);
    }

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap!!


}

interface DownloadImageCallback {
    fun downloadComplete(bitmap: Bitmap)
    fun downFail(bitmap: Bitmap)
}


fun getImageRequestDensity(context: Context): Float {
    val density: Float
    val metrics = context.resources.displayMetrics


    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    // Check for network and reduce if Mobile Data Connected
    val networkInfo = connectivityManager.activeNetworkInfo
    if (metrics.density == 1f || networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
        // 3G
        density = 1f
    } else {
        // WIFI
        density = (if (metrics.density < 2) 1.5F else 2.0F)
    }

    Log.d("ImageUtils", "density is: $density")
    return density
}

fun getImageWidthWithDensityBasedOnContextShortEdge(context: Context): Int {

    val metrics = context.resources.displayMetrics

    var width = if (metrics.widthPixels > metrics.heightPixels) metrics.heightPixels else metrics.widthPixels

    width = (width / metrics.density).toInt()//get the width of density 1.0

    width = (width * getImageRequestDensity(context)).toInt()

    return width
}


fun getImageWidthWithDensityBasedOnViewWidth(context: Context, viewWidth: Int): Int {
    val metrics = context.resources.displayMetrics

    val width: Int
    val widthDensityOne: Int

    widthDensityOne = (viewWidth / metrics.density).toInt()//get the width of density 1.0

    width = (widthDensityOne * getImageRequestDensity(context)).toInt()

    return width
}

fun Activity.fetchSvg(imageView: ImageView, url: String?) {
    url?.let {
        try {
            Glide.with(this)
                    .`as`(PictureDrawable::class.java)
                    .load(url)
                    .listener(SvgSoftwareLayerSetter())
                    .into(imageView)
        } catch (e: Exception) {
            //Crashlytics.logException(e)
            e.printStackTrace()
        }
    }
}