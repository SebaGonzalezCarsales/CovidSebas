package au.com.carsales.basemodule.viewcomponent.common

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

@Deprecated("Use StyleViewData instead")
open class StyleViewModel(private val backgroundColourStr: String? = null, private val borderColourStr: String? = null,
                          private val textColourStr: String? = null) : Parcelable {
    var backgroundColor: Int? = null

    var borderColor: Int? = null

    var textColor: Int? = null

    init {
        parseColors(backgroundColourStr, borderColourStr, textColourStr)
    }

    constructor(source: Parcel) : this() {
        parseColors(source.readString(), source.readString(), source.readString())
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(backgroundColourStr)
        dest.writeString(borderColourStr)
        dest.writeString(textColourStr)
    }

    private fun parseColors(backgroundColourStr: String? = null, borderColourStr: String? = null,
                            textColourStr: String? = null) {
        try {
            if (backgroundColourStr != null) {
                backgroundColor = Color.parseColor("#$backgroundColourStr")
            }

            if (borderColourStr != null) {
                borderColor = Color.parseColor("#$borderColourStr")
            }

            if (textColourStr != null) {
                textColor = Color.parseColor("#$textColourStr")
            }

        } catch (e: Exception) {

        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StyleViewModel> = object : Parcelable.Creator<StyleViewModel> {
            override fun createFromParcel(source: Parcel): StyleViewModel = StyleViewModel(source)
            override fun newArray(size: Int): Array<StyleViewModel?> = arrayOfNulls(size)
        }
    }
}