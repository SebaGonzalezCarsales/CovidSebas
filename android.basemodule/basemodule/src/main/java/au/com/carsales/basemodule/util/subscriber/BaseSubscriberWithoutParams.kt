package au.com.carsales.basemodule.util.subscriber

import android.content.Context
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData

open class BaseSubscriberWithoutParams<D>(context: Context?) : APIExceptionHandlerSubscriber<D>(context) {

    override fun onComplete() {}
    override fun onNext(t: D?) {}
    override fun onAPIError(apiErrorViewData: APIErrorViewData?) {}
    override fun onError(t: Throwable?) { super.onError(t) }
}