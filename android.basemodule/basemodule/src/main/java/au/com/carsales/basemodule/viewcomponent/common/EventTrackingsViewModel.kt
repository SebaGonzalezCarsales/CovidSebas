package au.com.carsales.basemodule.viewcomponent.common

import android.os.Parcel
import android.os.Parcelable

class EventTrackingsViewModel() : Parcelable {

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<EventTrackingsViewModel> = object : Parcelable.Creator<EventTrackingsViewModel> {
            override fun createFromParcel(source: Parcel): EventTrackingsViewModel = EventTrackingsViewModel(source)
            override fun newArray(size: Int): Array<EventTrackingsViewModel?> = arrayOfNulls(size)
        }
    }
}