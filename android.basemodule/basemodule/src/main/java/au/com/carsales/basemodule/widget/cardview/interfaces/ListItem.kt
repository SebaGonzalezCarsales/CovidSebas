package au.com.carsales.basemodule.widget.cardview.interfaces

import androidx.annotation.IntDef

abstract class ListItem {

    @get:ItemType
    abstract val type: Int

    @IntDef(TYPE_HEADER, TYPE_ANSWER_TOP, TYPE_ANSWER_MIDDLE, TYPE_ANSWER_BOTTOM, TYPE_ANSWER_SINGLE)
    @kotlin.annotation.Retention
    annotation class ItemType

    companion object {

        const val TYPE_HEADER = 0
        const val TYPE_ANSWER_TOP = 1
        const val TYPE_ANSWER_MIDDLE = 2
        const val TYPE_ANSWER_BOTTOM = 3
        const val TYPE_ANSWER_SINGLE = 4
    }
}
