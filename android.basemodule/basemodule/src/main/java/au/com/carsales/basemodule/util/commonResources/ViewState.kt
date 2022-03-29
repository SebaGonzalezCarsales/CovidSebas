package au.com.carsales.basemodule.util.commonResources

import android.content.Context
import au.com.carsales.basemodule.extension.empty

data class ViewState (val type: StateType, val errorMessage: String? = null, val errorMessageResId: Int? = null) {
    fun getMessage(context: Context): String {
        return when {
            errorMessageResId != null -> context.getString(errorMessageResId)
            !errorMessage.isNullOrEmpty() -> errorMessage
            else -> {
                String.empty()
            }
        }
    }
}
enum class StateType {
    SUCCESS,
    ERROR,
    LOADING,
    DEFAULT
}