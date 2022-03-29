package au.com.carsales.basemodule.tracking.url


import android.content.Context
import android.util.Log
import au.com.carsales.basemodule.api.domain.interactor.dynamicUrlCall.DynamicUrlCallUseCase
import au.com.carsales.basemodule.baseConcreteAppComponent
import javax.inject.Inject

class UrlTracker(context: Context) {

    companion object {
        val TAG = "UrlTracker"
        private var instance: UrlTracker? = null
        fun getInstance(context: Context): UrlTracker {
            if (instance == null) {
                instance = UrlTracker(context)
            }
            return instance as UrlTracker
        }
    }

    @Inject
    lateinit var dynamicUrlCallUseCase: DynamicUrlCallUseCase;

    init {
        context.baseConcreteAppComponent().inject(this)
    }


    fun track(trackingUrl: String?, trackingUrls: List<String?>?) {
        trackingUrl?.let {
            dynamicUrlCallUseCase.execute(it).subscribe({
                Log.d(TAG, "onComplete")

            }, {
                Log.d(TAG, "${it.message}")
            })
        }

        trackingUrls?.forEach { it ->
            it?.let { url ->
                dynamicUrlCallUseCase.execute(url).subscribe({
                    Log.d(TAG, "onComplete")

                }, {
                    Log.d(TAG, "${it.message}")
                })

            }

        }

    }

}

