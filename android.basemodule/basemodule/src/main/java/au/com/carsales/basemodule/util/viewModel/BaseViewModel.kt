package au.com.carsales.basemodule.util.viewModel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.model.BaseException
import au.com.carsales.basemodule.tracking.BaseTracker
import au.com.carsales.basemodule.tracking.Tracker
import au.com.carsales.basemodule.util.ObservableViewModel
import au.com.carsales.basemodule.util.commonResources.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * Author: Iroman
 * Includes the tenant parameter in all ViewModel implemented
 */
abstract class BaseViewModel : ObservableViewModel() {
    val onError = MutableLiveData<BaseException>()

    protected fun MutableLiveData<BaseException>.post(message: String) {
        postValue(BaseException(messageText = message))
    }

    protected fun MutableLiveData<BaseException>.post(messageResId: Int) {
        postValue(BaseException(messageResId = messageResId))
    }

    protected fun MutableLiveData<BaseException>.post(exception: Exception) {
        postValue(BaseException(exception = exception))
    }

    var isLoadingMorePagesLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var tenant: String?
        get() = BaseModuleApplication.tenant
        set(@Suppress("UNUSED_PARAMETER") value) {}
    var retailApiVersion: String?
        get() = BaseModuleApplication.retailApiVersion
        set(@Suppress("UNUSED_PARAMETER") value) {}
    var baseTracker: BaseTracker = Tracker()

    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)
    var isEmpty: ObservableBoolean = ObservableBoolean(false)
    var isRefreshing: ObservableBoolean = ObservableBoolean(false)

    //This observable boolean is to prevent showing
    // the main XML before data arrives (mainly used for this purpose)
    var hasContent: ObservableBoolean = ObservableBoolean(false)

    open fun setShowLoading(willRefresh: Boolean = false) {
        isLoading.set(!willRefresh)
        isRefreshing.set(willRefresh)
        isError.set(false)
        isEmpty.set(false)
    }

    open fun setShowError() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(true)
        isEmpty.set(false)
    }

    open fun setShowEmpty() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(false)
        isEmpty.set(true)
    }

    open fun setShowContent() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(false)
        isEmpty.set(false)
    }

    //region stateLiveData
    private val stateLiveData: MutableLiveData<State<*>> = MutableLiveData()

    fun observeStateLiveData(lifecycleOwner: LifecycleOwner, observer: Observer<State<*>>) {
        stateLiveData.observe(lifecycleOwner, observer)
    }

    fun observeForeverStateLiveData(observer: Observer<State<*>>) {
        stateLiveData.observeForever(observer)
    }

    fun observeStateLiveData(
            lifecycleOwner: LifecycleOwner,
            onSuccess: ((State.Success<*>) -> Unit?)? = null,
            onLoading: ((State.Loading<*>) -> Unit?)? = null,
            onDefault: ((State.Default<*>) -> Unit?)? = null,
            onError: ((State.Error<*>) -> Unit?)? = null,
            onErrorEmpty: ((State.ErrorEmpty<*>) -> Unit?)? = null,
            onSignOut: ((State.SignOut<*>) -> Unit?)? = null,
            onPagingError: ((State.PagingError<*>) -> Unit?)? = null,
            onLoadingRefresh: ((State.LoadingRefresh<*>) -> Unit?)? = null
    ) {
        stateLiveData.observe(lifecycleOwner, Observer<State<*>> {
            when (it) {
                is State.Success -> onSuccess?.invoke(it)
                is State.Loading -> onLoading?.invoke(it)
                is State.Error -> onError?.invoke(it)
                is State.LoadingRefresh -> onLoadingRefresh?.invoke(it)
                is State.Default -> onDefault?.invoke(it)
                is State.SignOut -> onSignOut?.invoke(it)
                is State.PagingError -> onPagingError?.invoke(it)
                is State.ErrorEmpty -> onErrorEmpty?.invoke(it)
            }
        })
    }

    fun <T> getStateValue(): State<T>? {
        return stateLiveData.value as? State<T>
    }

    fun <T> postStateValue(value: State<T>) {
        stateLiveData.postValue(value)
    }

    //endregion stateLiveData

    protected fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                isLoading.set(true)
                block.invoke()
            } catch (e: Exception) {
                onError.post(e)
            } finally {
                isLoading.set(false)
            }
        }
    }

    protected fun launchDataLoad(jobs: List<Deferred<Any?>>): Job {
        return viewModelScope.launch {
            try {
                isLoading.set(true)
                jobs.awaitAll()
            } catch (e: Exception) {
                onError.post(e)
            } finally {
                isLoading.set(false)
            }
        }
    }
}

fun MutableLiveData<ViewState>.setLoadingState() {
    this.value = ViewState(type = StateType.LOADING)
}

fun MutableLiveData<ViewState>.setDefaultState() {
    this.value = ViewState(type = StateType.DEFAULT)
}

fun MutableLiveData<ViewState>.setSuccessState() {
    this.value = ViewState(type = StateType.SUCCESS)
}

fun MutableLiveData<ViewState>.setErrorState() {
    this.value = ViewState(type = StateType.ERROR)
}

fun MutableLiveData<ViewState>.observeViewStateLiveData(
        lifecycleOwner: LifecycleOwner,
        onSuccess: ((ViewState) -> Unit?)? = null,
        onLoading: ((ViewState) -> Unit?)? = null,
        onError: ((ViewState) -> Unit?)? = null,
        onDefault: ((ViewState) -> Unit?)? = null) {
    this.observe(lifecycleOwner, Observer<ViewState> {
        when (it.type) {
            StateType.SUCCESS -> onSuccess?.invoke(it)
            StateType.LOADING -> onLoading?.invoke(it)
            StateType.ERROR -> onError?.invoke(it)
            StateType.DEFAULT -> onDefault?.invoke(it)
        }
    })
}


fun <T> MutableLiveData<State<T>>.observeStateLiveData(
        lifecycleOwner: LifecycleOwner,
        onSuccess: ((State.Success<T>) -> Unit?)? = null,
        onLoading: ((State.Loading<T>) -> Unit?)? = null,
        onDefault: ((State.Default<T>) -> Unit?)? = null,
        onError: ((State.Error<*>) -> Unit?)? = null,
        onErrorEmpty: ((State.ErrorEmpty<*>) -> Unit?)? = null,
        onSignOut: ((State.SignOut<*>) -> Unit?)? = null,
        onPagingError: ((State.PagingError<*>) -> Unit?)? = null,
        onLoadingRefresh: ((State.LoadingRefresh<*>) -> Unit?)? = null
) {
    this.removeObservers(lifecycleOwner)
    this.observe(lifecycleOwner, Observer<State<T>> {
        when (it) {
            is State.Success -> onSuccess?.invoke(it)
            is State.Loading -> onLoading?.invoke(it)
            is State.Error -> onError?.invoke(it)
            is State.LoadingRefresh -> onLoadingRefresh?.invoke(it)
            is State.Default -> onDefault?.invoke(it)
            is State.SignOut -> onSignOut?.invoke(it)
            is State.PagingError -> onPagingError?.invoke(it)
            is State.ErrorEmpty -> onErrorEmpty?.invoke(it)
        }
    })
}

fun <T> MutableLiveData<Resource<T>>.observeResourceStateLiveData(
        lifecycleOwner: LifecycleOwner,
        onSuccess: ((T?) -> Unit?)? = null,
        onLoading: ((T?) -> Unit?)? = null,
        onError: ((T?) -> Unit?)? = null,
        onDefault: ((T?) -> Unit?)? = null,
        onPagingSuccess: ((T?) -> Unit?)? = null,
        onSignOut: ((T?) -> Unit?)? = null,
        onPagingError: ((T?) -> Unit?)? = null,
        onLoadingRefresh: ((T?) -> Unit?)? = null,
        onRecall: ((T?) -> Unit?)? = null,
        onRefreshToken: ((T?) -> Unit?)? = null) {
    this.observe(lifecycleOwner, Observer<Resource<T>> {
        when (it.status) {
            ResourceState.SUCCESS -> onSuccess?.invoke(it.data)
            ResourceState.LOADING -> onLoading?.invoke(it.data)
            ResourceState.ERROR -> onError?.invoke(it.data)
            ResourceState.SIGN_OUT -> onSignOut?.invoke(it.data)
            ResourceState.DEFAULT -> onDefault?.invoke(it.data)
            ResourceState.LOADING_REFRESH -> onLoadingRefresh?.invoke(it.data)
            ResourceState.PAGING_SUCCESS -> onPagingSuccess?.invoke(it.data)
            ResourceState.PAGING_ERROR -> onPagingError?.invoke(it.data)
            ResourceState.REFRESH_TOKEN -> onRefreshToken?.invoke(it.data)
            ResourceState.RECALL -> onRecall?.invoke(it.data)
        }
    })
}
