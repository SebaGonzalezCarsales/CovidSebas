package au.com.carsales.basemodule.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.annotation.Nullable
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


class ImageUtils(var context: Context?) {

    // Decodes image and scales it to reduce memory consumption
    fun decodeFile(f: File): Bitmap? {
        try {

//            return BitmapFactory.decodeFile(f.absolutePath);
//            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)

            // The new size we want to scale to
            val REQUIRED_SIZE = 1024

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2
            }

            // Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }

        return null
    }

    /*
    * #Deprecate "path: String?", not working for android 29
    * */
    @Throws(IOException::class)
    fun setDefaultRotatePhoto(bitmap: Bitmap, path: String? = null, selectedImageUri: Uri? = null): Bitmap? {

        val ei = selectedImageUri?.let {
            getExifInterface(context, selectedImageUri);
        } ?: path?.let { ExifInterface(it) }
        val orientation = ei?.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED)

        return when (orientation) {

            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)

            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height,
                matrix, true)
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        val bitmap: Bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    fun fromGallery(selectedImageUri: Uri): Bitmap? {

        try {
            val bm = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImageUri)
            return if (bm != null) {
                ImageUtils(context).setDefaultRotatePhoto(bm, getRealPathFromURI(selectedImageUri)!!, selectedImageUri)
            } else null

        } catch (e: IOException) {
            Log.e("", "-- Error in setting image");
        } catch (oom: OutOfMemoryError) {
            Log.e("", "-- OOM Error in setting image");
        }
        return null;
    }


    @Nullable
    fun getExifInterface(context: Context?, uri: Uri): ExifInterface? {
        try {
            val path = uri.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val inputStream = context?.contentResolver?.openInputStream(uri)
                inputStream?.let {
                    return ExifInterface(inputStream)
                } ?: return null
            } else {
                if (path.startsWith("file://")) {
                    return ExifInterface(path)
                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor = context!!.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor!!.moveToFirst()
            val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor!!.getString(idx)
            cursor!!.close()
        }
        return result
    }
}


/*
* Return the current display metrics that are in effect for this resource
 * object
 * Means, not including system bar, etc
 * So in portrait mode if the
* */
fun getShortEdgePixelsNoSystemBar(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return if (metrics.widthPixels > metrics.heightPixels) metrics.heightPixels else metrics.widthPixels
}


//@Deprecated("better version: getShortEdgeDisplayPixels(context: Context)")
fun getShortEdgeDisplayPixels(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    if (Build.VERSION.SDK_INT >= 17) {
        display.getRealSize(size)
    } else {
        display.getSize(size) // correct for devices with hardware navigation buttons
    }

    return if (size.x > size.y) size.y else size.x
}

fun getLongEdgeDisplayPixels(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getRealSize(size)
    return if (size.x > size.y) size.x else size.y
}



