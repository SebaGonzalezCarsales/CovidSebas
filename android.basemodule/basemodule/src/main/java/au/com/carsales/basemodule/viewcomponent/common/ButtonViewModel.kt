package au.com.carsales.basemodule.viewcomponent.common

import androidx.databinding.ObservableField
import android.os.Parcel
import android.os.Parcelable

import au.com.carsales.basemodule.viewcomponent.BaseActionViewModel

/**
 * Created by kelly.wang on 19/09/2016.
 */
class ButtonViewModel : Parcelable {

    var text = ObservableField<String>()

    var type: String? = null
    var styleViewModel: StyleViewModel? = null
    var actionViewModel: BaseActionViewModel? = null
    var eventTrackingsViewModel: EventTrackingsViewModel? = null

    constructor(type: String?, text: String?, styleViewModel: StyleViewModel?, actionViewModel: BaseActionViewModel?,
                eventTrackingsViewModel: EventTrackingsViewModel?) {
        this.text.set(text)
        this.type = type
        this.styleViewModel = styleViewModel
        this.actionViewModel = actionViewModel
        this.eventTrackingsViewModel = eventTrackingsViewModel
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeSerializable(this.text)
        dest.writeString(this.type)
        dest.writeParcelable(this.styleViewModel, flags)
        dest.writeParcelable(this.actionViewModel, flags)
        dest.writeParcelable(this.eventTrackingsViewModel, flags)
    }

    protected constructor(`in`: Parcel) {
        this.text = `in`.readSerializable() as ObservableField<String>
        this.type = `in`.readString()
        this.styleViewModel = `in`.readParcelable(StyleViewModel::class.java.classLoader)
        this.actionViewModel = `in`.readParcelable(BaseActionViewModel::class.java.classLoader)
        this.eventTrackingsViewModel = `in`.readParcelable(EventTrackingsViewModel::class.java.classLoader)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ButtonViewModel> = object : Parcelable.Creator<ButtonViewModel> {
            override fun createFromParcel(source: Parcel): ButtonViewModel {
                return ButtonViewModel(source)
            }

            override fun newArray(size: Int): Array<ButtonViewModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}