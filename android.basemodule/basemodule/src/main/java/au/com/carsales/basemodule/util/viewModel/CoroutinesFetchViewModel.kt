package au.com.carsales.basemodule.util.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.handleAPIErrorResponse
import au.com.carsales.basemodule.util.Mapper
import au.com.carsales.basemodule.util.commonResources.State
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import kotlinx.coroutines.launch

/**
 * Created by Dan on 19, November, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * Viewmodel used to fetch data from server using Coroutines.
 * It uses the actual Mapper<Destiny, Source> and UseCase structure.
 *
 * <D> is the data provided directly from server. (Before mapping)
 * <VD> is the viewData already mapped from the data
 */
abstract class CoroutinesFetchViewModel<D, VD> : BaseViewModel() {

    /**
     * Used to manage states of the data provided by
     * the server. Has a "_" so a LiveData object can
     * be used in the child ViewModel and use a property
     * getter that calls this one. It's cleaner this way
     */
    abstract var _liveData : MutableLiveData<State<VD>>

    /**
     * The mapper needed to transform Data (D) to
     * ViewData (VD)
     */
    abstract var mapper: Mapper<VD, D>

    /**
     * Optional custom error message to be used in
     * error cases
     */
    private var customErrorMessage: String = String.empty()


    /**
     * This is where the fetch from server is being
     * done and it will be done inside the Coroutine
     */
    abstract suspend fun getData() : State<D>

    /**
     * Optional custom success code to be triggered
     * after all success code
     */
    abstract fun customSuccess(viewData: VD)

    /**
     * Optional custom error code to be triggered
     * after all error code
     */
    abstract fun customError(errorMessage: String, apiErrorViewData: APIErrorViewData?)

    /**
     * Manage the loading states changing the
     * MutableLiveData object states and the
     * BaseViewModel parent ObservableBoolean
     * objects
     *
     * @param showLoading       true if the loading will be shown, false otherwise
     */
    private fun manageLoadingStates(showLoading: Boolean) {
        if (showLoading) {
            setShowLoading()
            hasContent.set(false)
            _liveData.postValue(State.loading())
        } else {
            setShowLoading(willRefresh = true)
            _liveData.postValue(State.loadingRefresh())
        }
    }

    /**
     * Fetch the data from the server using a
     * Coroutine inside this ViewModel scope
     *
     * @param showLoading       true if the loading will be shown, false otherwise
     */
    fun fetchData(showLoading: Boolean = true) {

        manageLoadingStates(showLoading)

        viewModelScope.launch {
            manageResult(getData())
        }
    }

    /**
     * Manage the result from the Coroutine
     *
     * @param data      the data from the server inside the State
     */
    private fun manageResult(data: State<D>) {

        when(data) {
            is State.Success -> manageSuccessResult(data.data)
            is State.Error -> manageErrorResult(data.errorMessage?: customErrorMessage, data.errorViewData)
        }
    }

    /**
     * Manages the result when is success state.
     *
     * @param data      the data from the server
     */
    private fun manageSuccessResult(data: D) {

        val viewData = mapper.executeMapping(data)

        if(viewData != null){
            setShowContent()
            hasContent.set(true)
            _liveData.postValue((State.success(viewData)))

            customSuccess(viewData)
        } else {
            setShowError()
            hasContent.set(false)
            _liveData.postValue(State.error(customErrorMessage, handleAPIErrorResponse(context, null) ))
        }
    }

    /**
     * Manages the result in the case of error state.
     *
     * @param errorMessage      the error message from server
     * @param errorViewData     the generated APIErrorViewData object
     */
    private fun manageErrorResult(errorMessage: String, errorViewData: APIErrorViewData?) {
        hasContent.set(false)
        setShowError()
        _liveData.postValue(State.error(errorMessage, errorViewData))

        customError(errorMessage, errorViewData)
    }
}