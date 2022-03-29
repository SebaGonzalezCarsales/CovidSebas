package au.com.carsales.basemodule.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.annotation.StringDef
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class URIS {
    var photoUriForProviderExternalAccess: Uri? = null
    var photoUriForInternalAccess: Uri? = null
}


const val TYPE_IMAGE = "image"
const val TYPE_PDF = "pdf"


@Retention(AnnotationRetention.SOURCE)
@StringDef(TYPE_IMAGE, TYPE_PDF)
annotation class FileType

@Throws(IOException::class)
fun Context.getProviderOutputMediaFileUri(): URIS {
    val uris = URIS()

    val file = createEmptyFile(TYPE_IMAGE)

    uris.photoUriForInternalAccess = Uri.fromFile(file)
    uris.photoUriForProviderExternalAccess = FileProvider.getUriForFile(this,
            "$packageName.fileprovider",
            file!!)

    return uris

}


@Throws(IOException::class)
fun Context.createEmptyFile(@FileType fileType: String, isExternal: Boolean = true): File? {
    var mediaStorageDir: File? = null
    when (isExternal) {
        true -> {
            mediaStorageDir = this.getExternalFilesDir(null)
        }
        false -> {
            mediaStorageDir = this.filesDir
        }

    }

    // Create the storage directory if it does not exist
    if (!mediaStorageDir!!.exists()) {
        if (!mediaStorageDir.mkdirs()) {
            Log.d("getOutputMediaFile", "failed to create directory")
            return null
        }
    }
    // Create a media file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    var fileName: String? = null

    when (fileType) {
        TYPE_IMAGE -> {
            fileName = "IMG_" + timeStamp + "_.jpg"
        }
        TYPE_PDF -> {
            fileName = "Doc" + timeStamp + "_.pdf"
        }
        else -> {
            fileName = "File" + timeStamp + "_"
        }
    }

    return File(mediaStorageDir, fileName)

}

