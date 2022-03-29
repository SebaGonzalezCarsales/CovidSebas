package au.com.carsales.basemodule.extension

import androidx.fragment.app.Fragment

fun androidx.fragment.app.Fragment.tagName(): String {
    return this::class.java.simpleName
}


fun androidx.fragment.app.Fragment.getWidthPixels(): Int? {
    var metrics = activity?.getResources()?.getDisplayMetrics() ?: null
    return metrics?.widthPixels
}



fun androidx.fragment.app.Fragment.getShortEdgePixels(): Int? {
    var metrics = activity?.getResources()?.getDisplayMetrics() ?: null
    return metrics?.run { if (widthPixels > heightPixels) heightPixels else widthPixels }
}
