package au.com.carsales.basemodule.util.subscriber

import android.content.Context
import au.com.carsales.basemodule.extension.handleAPIErrorResponse
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by Dan on 26, June, 2019
 * Copyright (c) 2019 Carsales. All rights reserved.
 *
 * Transform data error to API error view data
 */
abstract class APIExceptionHandlerSubscriber<T>(val context: Context?) : DisposableSubscriber<T>() {

    abstract fun onAPIError(apiErrorViewData: APIErrorViewData?)
    abstract override fun onNext(t: T?)

    override fun onComplete(){}

    override fun onError(t: Throwable?) {
        onAPIError(handleAPIErrorResponse(context, t))
    }
}