package au.com.carsales.basemodule.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import android.util.Log

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.initObserver(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<T> { t -> observer.invoke(t) })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
        //Crashlytics.log(Log.ERROR, lifecycleOwner.lifecycle.toString(), "LiveDataExtension - initObserver")
    }
}


fun LiveData<Boolean>.initBooleanObserver(lifecycleOwner: LifecycleOwner, onTrue: (() -> Unit)? = null, onFalse: (() -> Unit)? = null) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<Boolean> { t ->
            when {
                t -> {
                    onTrue?.invoke()
                }
                else -> onFalse?.invoke()
            }
        })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
        //Crashlytics.log(Log.ERROR, lifecycleOwner.lifecycle.toString(), "LiveDataExtension - initBooleanObserver")
    }
}

fun <T> LiveData<T>.initObserverForever(observer: Observer<T>) {
    removeObserver(observer)
    observeForever { t -> observer.onChanged(t) }
}