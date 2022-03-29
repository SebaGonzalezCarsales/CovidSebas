package au.com.carsales.basemodule.model

import android.content.Context
import au.com.carsales.basemodule.extension.empty

data class BaseException(val messageResId: Int? = null, val exception: Exception? = null, val messageText: String? = null) {
    fun getMessage(context: Context): String {
        return when {
            messageResId != null -> context.getString(messageResId)
            !messageText.isNullOrEmpty() -> messageText
            exception != null -> exception.localizedMessage
            else -> {
                String.empty()
            }
        }
    }
}