package au.com.carsales.basemodule.util.subscriber

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import au.com.carsales.basemodule.util.commonResources.Resource
import au.com.carsales.basemodule.util.commonResources.ResourceState
import au.com.carsales.basemodule.util.viewModel.subscriber.APIExceptionHandlerSubscriber
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData

/**
 * Created by Dan on 26, June, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * Generic base subscriber class.
 * @param context       Context to use optionally in resources or related functionality
 * @param mapper        Mapper to map corresponding data to view data
 * @param liveData      Mutable live data to work with resource states
 * @param isLoading     Optional ObservableBoolean to work most often with databinding in layout
 */
class VoidResponseAPISubscriber<D>(context: Context?,
                                   val liveData: MutableLiveData<Resource<String>>,
                                   val isLoading: ObservableBoolean? = null,
                                   val isError: ObservableBoolean? = null) : APIExceptionHandlerSubscriber<D>(context) {

    override fun onComplete() {

        isLoading?.set(false)
        liveData.postValue(Resource(ResourceState.SUCCESS, "ok", null))
    }

    override fun onNext(t: D) {
    }

    override fun onAPIError(apiErrorViewData: APIErrorViewData?) {
        isLoading?.set(false)
        isError?.set(true)
        liveData.postValue(Resource(ResourceState.ERROR, null, "", apiErrorViewData = apiErrorViewData))
    }
}