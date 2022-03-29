package au.com.carsales.basemodule.util

import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.initObserver
import au.com.carsales.basemodule.util.commonResources.Resource
import au.com.carsales.basemodule.util.commonResources.ResourceState
import au.com.carsales.basemodule.widget.adapter.base.BindableAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * Created by anibalbastias on 2019-08-09.
 */

inline fun <reified T : View> LinearLayout.renderViewComponent(index: Int, createViewBody: () -> T, setViewBody: (T) -> Unit?) {

    context?.let {
        val viewComponent: T
        // for case that add views first time
        if (index == childCount) {
            viewComponent = createViewBody.invoke()
            addView(viewComponent)

        } else if (index < childCount) { //for case that update views
            val currentViewInIndex = get(index)

            if (currentViewInIndex is T) {
                setViewBody(currentViewInIndex)
            } else {
                removeView(currentViewInIndex)
                viewComponent = createViewBody.invoke()

                addView(viewComponent)
            }
        }
    }
}

fun SwipeRefreshLayout.initSwipe(onSwipeUnit: (() -> Unit)?) {
    this.setColorSchemeColors(ContextCompat.getColor(context, R.color.primaryColor))
    this.setOnRefreshListener { onSwipeUnit?.invoke() }
}

/**
 * Implements a custom observer
 * to a MutableLiveData object
 */
fun <T> Fragment.implementObserver(mutableLiveData: MutableLiveData<Resource<T>>,
                                   successBlock: (T) -> Unit = {},
                                   loadingBlock: () -> Unit = {},
                                   errorBlock: (String?) -> Unit = {},
                                   defaultBlock: () -> Unit = {},
                                   codeBlock: () -> Unit = {},
                                   emptyBlock: () -> Unit = {},
                                   hideLoadingBlock: () -> Unit = {}) {

    handleObserver(mutableLiveData, defaultBlock, successBlock, loadingBlock, errorBlock, codeBlock, emptyBlock, hideLoadingBlock)
}


private fun <T> Fragment.handleObserver(mutableLiveData: MutableLiveData<Resource<T>>,
                                        defaultBlock: () -> Unit,
                                        successBlock: (T) -> Unit,
                                        loadingBlock: () -> Unit,
                                        errorBlock: (String?) -> Unit,
                                        codeBlock: () -> Unit,
                                        emptyBlock: () -> Unit,
                                        hideLoadingBlock: () -> Unit = {}) {
    mutableLiveData.initObserver(this) {
        handleStateObservers(codeBlock, it, defaultBlock, successBlock, loadingBlock, errorBlock, emptyBlock, hideLoadingBlock)
    }
}

private fun <T> handleStateObservers(codeBlock: () -> Unit,
                                     it: Resource<T>?,
                                     defaultBlock: () -> Unit,
                                     successBlock: (T) -> Unit,
                                     loadingBlock: () -> Unit,
                                     errorBlock: (String?) -> Unit,
                                     emptyBlock: () -> Unit,
                                     hideLoadingBlock: () -> Unit = {}) {
    codeBlock()

    when (it?.status) {
        ResourceState.DEFAULT -> defaultBlock()
        ResourceState.SUCCESS -> {
            hideLoadingBlock()
            successBlock(it.data!!)
        }
        ResourceState.LOADING -> loadingBlock()
        ResourceState.ERROR -> {
            hideLoadingBlock()
            errorBlock(it.message)
        }
        ResourceState.EMPTY -> {
            hideLoadingBlock()
            emptyBlock()
        }
        else -> {
        }
    }
}

inline fun <reified T> LiveData<T>.subscribe(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, observer)
}

fun <T> CoroutineScope.processJobAsync(block: suspend () -> T, result: (T) -> Unit, onError: (Exception) -> Unit): Deferred<Unit> {
    return async {
        try {
            result(block())
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e)
        }
    }
}

fun Boolean?.isNullOrFalse(): Boolean {
    return ((this == null).or(this == false))
}

fun Boolean?.orFalse(): Boolean {
    return !this.isNullOrFalse()
}

fun Boolean?.isTrue(): Boolean {
    return this == true
}

fun <T> T?.orElse(otherValue: T): T {
    return this ?: otherValue
}

fun String?.orNull(): String? {
    return if(!this.isNullOrEmpty()) this else null
}

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).setData(data)
    }
}

fun <E> MutableList<E>.findToRemove(predicate: (E) -> Boolean): Boolean {
    var removed = false
    val each: MutableIterator<E> = iterator()
    while (each.hasNext()) {
        if (predicate(each.next())) {
            each.remove()
            removed = true
        }
    }
    return removed
}