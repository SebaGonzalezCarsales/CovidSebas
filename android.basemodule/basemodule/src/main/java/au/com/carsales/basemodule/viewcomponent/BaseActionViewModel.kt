package au.com.carsales.basemodule.viewcomponent


import android.os.Parcel
import android.os.Parcelable
import au.com.carsales.basemodule.tracking.model.TrackingViewData

/**
 * Created by kelly.wang on 25/01/2017.
 */

open class BaseActionViewModel() : Parcelable {
    var actionType: String? = null
    var target: String? = null
    var trackingViewData: TrackingViewData? = null
    var clickTrackingUrls: List<String>? = null

    constructor(parcel: Parcel) : this() {
        actionType = parcel.readString()
        target = parcel.readString()
        trackingViewData = parcel.readSerializable() as TrackingViewData?
        clickTrackingUrls = parcel.createStringArrayList()
    }


    constructor(actionType: String? = null, target: String? = null, tackingViewData: TrackingViewData? = null,
                clickTrackingUrls: List<String>? = null) : this() {
        this.actionType = actionType
        this.target = target
        this.trackingViewData = tackingViewData
        this.clickTrackingUrls = clickTrackingUrls?.toMutableList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(actionType)
        parcel.writeString(target)
        parcel.writeSerializable(trackingViewData)
        parcel.writeStringList(clickTrackingUrls)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseActionViewModel> {
        override fun createFromParcel(parcel: Parcel): BaseActionViewModel {
            return BaseActionViewModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseActionViewModel?> {
            return arrayOfNulls(size)
        }
    }


}
