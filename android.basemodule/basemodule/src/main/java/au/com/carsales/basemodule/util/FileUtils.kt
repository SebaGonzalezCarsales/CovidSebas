package au.com.carsales.basemodule.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.getAppContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {

    companion object {
        const val MAX_WIDTH_HEIGHT_RESOLUTION = 1024 // x * 2 = 2048
    }


    private fun getRealPathFromURI(context: Context, contentUri: Uri): String {

        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            return contentUri?.path?:String.empty()
        } finally {
            cursor?.close()
        }
    }

    // File to MultiPart method
    fun getMultiPartFormFromFile(context: Context, uriFile: Uri): File {
        return File(getRealPathFromURI(context, uriFile))
    }

    fun obtainFileBitmap(bitmapForSaved: Bitmap, isEdit: Boolean, filename: String,
                         bitmapCompressedListener: BitmapCompressedListener) {
        val bitmapCompressTask = BitmapCompressTask(filename, bitmapForSaved, isEdit, bitmapCompressedListener)
        bitmapCompressTask.execute()
    }

    class BitmapCompressTask(private val filename: String, private val bitmapForSaved: Bitmap?,
                             private val isEdit: Boolean,
                             private val bitmapListener: BitmapCompressedListener) : AsyncTask<String, String, File>() {

        override fun doInBackground(vararg strings: String): File? {

            //create a file to write bitmap data
            var f: File? = File(getAppContext().cacheDir, filename)
            try {
                f!!.createNewFile()

                // original measurements
                if (bitmapForSaved != null) {
                    val origWidth = bitmapForSaved.width
                    Log.e("BITMAP", (2 * bitmapForSaved.width).toString() + " - " + 2 * bitmapForSaved.height)

                    // Check if image contains 2048 px width size for compress
                    if (origWidth > MAX_WIDTH_HEIGHT_RESOLUTION) {
                        f = compressScaleBitmap(bitmapForSaved, isEdit)
                    } else {
                        //Convert bitmap to byte array
                        val bitmap = bitmapForSaved
                        val bos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos)
                        val bitmapData = bos.toByteArray()

                        //write the bytes in file
                        val fos = FileOutputStream(f)
                        fos.write(bitmapData)
                        fos.flush()
                        fos.close()
                    }
                } else {
                    f = null
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return f
        }

        override fun onPostExecute(file: File) {
            bitmapListener.onGetBitmapCompressed(file)
        }

        private fun compressScaleBitmap(bitmap: Bitmap, isEdit: Boolean): File {
            // original measurements
            val origWidth = bitmap.width
            val origHeight = bitmap.height

            var destWidth = origWidth

            val f = File(getAppContext().cacheDir.toString()
                    + File.separator + "image.jpg")

            // picture is wider than we want it, we calculate its target height
//            val destHeight = origHeight / (origWidth / destWidth)

            var destHeight = origHeight

            if (origWidth > FileUtils.MAX_WIDTH_HEIGHT_RESOLUTION) {
                destWidth = origWidth
            }

            if (origHeight > FileUtils.MAX_WIDTH_HEIGHT_RESOLUTION) {
                destHeight = origHeight
            }

            // we create an scaled bitmap so it reduces the image, not just trim it
            val b2 = Bitmap.createScaledBitmap(bitmap, destWidth, destHeight, false)
            val outStream = ByteArrayOutputStream()

            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
            b2.compress(Bitmap.CompressFormat.JPEG, 90, outStream)
            // we save the file, at least until we have made use of it
            try {
                f.createNewFile()
                //write the bytes in file
                val fo = FileOutputStream(f)
                fo.write(outStream.toByteArray())
                // remember close de FileOutput
                fo.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }


            return f
        }
    }
}

interface BitmapCompressedListener {
    fun onGetBitmapCompressed(file: File)
}