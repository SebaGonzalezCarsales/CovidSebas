package au.com.carsales.basemodule.util.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import au.com.carsales.basemodule.context
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.handleAPIErrorResponse
import au.com.carsales.basemodule.util.Mapper
import au.com.carsales.basemodule.util.commonResources.State
import au.com.carsales.basemodule.util.databinding.DatabindingStatesManagerViewModel
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import kotlinx.coroutines.launch

/**
 * Created by Dan on 19, November, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 */
abstract class MultipleCoroutinesFetchViewModel : DatabindingStatesManagerViewModel() {

    /**
     * Manage the loading states changing the
     * MutableLiveData object states and the
     * BaseViewModel parent ObservableBoolean
     * objects
     *
     * @param showLoading       true if the loading will be shown, false otherwise
     */
    private fun <VD> manageLoadingStates(showLoading: Boolean, liveData: MutableLiveData<State<VD>>) {
        if (showLoading) {
            setShowLoading()
            hasContent.set(false)
            liveData.postValue(State.loading())
        } else {
            setShowLoading(willRefresh = true)
            liveData.postValue(State.loadingRefresh())
        }
    }

    /**
     * Fetch the data from the server using a
     * Coroutine inside this ViewModel scope
     *
     * @param showLoading           true if the loading will be shown, false otherwise
     * @param manageLoadingStates   true if you prefer the loading states are managed
     *                              automatically in this class (see the function), false otherwise
     * @param liveData              the MutableLiveData object to post the new changes and States
     * @param getData               the suspend function with the Coroutine to be executed
     * @param manageResult          the code to manage the result of the Coroutine
     */
    fun <D, VD> fetchData(showLoading: Boolean,
                          manageLoadingStates: Boolean = true,
                          liveData: MutableLiveData<State<VD>>,
                          getData:suspend () -> State<D>,
                          manageResult: (State<D>) -> Unit) {

        if(manageLoadingStates)
            manageLoadingStates(showLoading, liveData)

        viewModelScope.launch {
            manageResult.invoke(getData())
        }
    }

    /**
     * Fetch the data from the server using a
     * Coroutine inside this ViewModel scope
     *
     * @param showLoading       true if the loading will be shown, false otherwise
     * @param manageLoadingStates   true if you prefer the loading states are managed
     *                              automatically in this class (see the function), false otherwise
     * @param liveData              the MutableLiveData object to post the new changes and States
     * @param getData               the suspend function with the Coroutine to be executed
     * @param mapper                the mapper that will transform the Data (D) to ViewData (VD)
     * @param customSuccess         optional custom success code to be executed after the auto success code (see function)
     * @param customError           optional custom error code to be executed
     * @param customErrorMessage    optional custom error message code to be executed
     */
    fun <D, VD> fetchData(showLoading: Boolean,
                          manageLoadingStates: Boolean = true,
                          liveData: MutableLiveData<State<VD>>,
                          getData:suspend () -> State<D>,
                          mapper: Mapper<VD, D>,
                          customSuccess: (VD)-> Unit = {},
                          customError: () -> Unit = {},
                          customErrorMessage: String = String.empty()) {

        if(manageLoadingStates)
            manageLoadingStates(showLoading, liveData)

        viewModelScope.launch {
            manageResult(getData(), mapper, liveData, customSuccess, customErrorMessage, customError)
        }
    }

    /**
     * Manage the result from the Coroutine
     *
     * @param data      the data from the server inside the State
     * @param mapper                the mapper that will transform the Data (D) to ViewData (VD)
     * @param liveData              the MutableLiveData object to post the new changes and States
     * @param customSuccess         optional custom success code to be executed after the auto success code (see function)
     * @param customError           optional custom error code to be executed
     * @param customErrorMessage    optional custom error message code to be executed
     */
    private fun <D, VD> manageResult(data: State<D>, mapper: Mapper<VD, D>,
                                     liveData: MutableLiveData<State<VD>>, customSuccess: (VD)-> Unit,
                                     customErrorMessage: String, customError: () -> Unit) {
        when(data) {
            is State.Success -> manageSuccessResult(data.data, mapper, liveData, customSuccess, customErrorMessage)
            is State.Error -> manageErrorResult(data.errorMessage?: customErrorMessage, data.errorViewData,
                                                liveData, customError)
        }
    }

    /**
     * Manages the result when is success state.
     *
     * @param data      the data from the server
     * @param mapper                the mapper that will transform the Data (D) to ViewData (VD)
     * @param liveData              the MutableLiveData object to post the new changes and States
     * @param customSuccess         optional custom success code to be executed after the auto success code (see function)
     * @param customErrorMessage    optional custom error message code to be executed
     */
    private fun <D, VD> manageSuccessResult(data: D, mapper: Mapper<VD, D>, liveData: MutableLiveData<State<VD>>,
                                            customSuccess: (VD)-> Unit, customErrorMessage: String) {

        val viewData = mapper.executeMapping(data)

        if(viewData != null) {
            setShowContent()
            hasContent.set(true)
            liveData.postValue((State.success(viewData)))

            customSuccess(viewData)
        } else {
            setShowError()
            hasContent.set(false)
            liveData.postValue(State.error(customErrorMessage, handleAPIErrorResponse(context, null) ))
        }
    }

    /**
     * Manages the result in the case of error state.
     *
     * @param errorMessage      the error message from server
     * @param errorViewData     the generated APIErrorViewData object
     * @param liveData              the MutableLiveData object to post the new changes and States
     * @param customError           optional custom error code to be executed
     */
    private fun <VD> manageErrorResult(errorMessage: String, errorViewData: APIErrorViewData?,
                                  liveData: MutableLiveData<State<VD>>, customError: () -> Unit) {
        hasContent.set(false)
        setShowError()
        liveData.postValue(State.error(errorMessage, errorViewData))

        customError()
    }

}