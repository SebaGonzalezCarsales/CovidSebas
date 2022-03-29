package au.com.carsales.basemodule.tracking.bi

import android.content.Context
import android.util.Log
import au.com.carsales.basemodule.api.domain.interactor.dynamicUrlCall.DynamicUrlCallUseCase
import au.com.carsales.basemodule.baseConcreteAppComponent
import au.com.carsales.basemodule.tracking.model.bi.BiViewData
import javax.inject.Inject


class BiTracker(context: Context) {

    companion object {
        val TAG = "BiTracker"
        private var instance: BiTracker? = null
        fun getInstance(context: Context): BiTracker {
            if (instance == null) {
                instance = BiTracker(context)
            }
            return instance as BiTracker
        }
    }

    @Inject
    lateinit var dynamicUrlCallUseCase: DynamicUrlCallUseCase;

    init {
        context.baseConcreteAppComponent().inject(this)
    }

    fun track(biModel: BiViewData?) {

        biModel?.trackingUrls?.forEach { it ->
            dynamicUrlCallUseCase.execute(it).subscribe({
                Log.d(TAG, "onComplete")

            }, {
                Log.d(TAG, "${it.message}")
            })
        }

    }

}