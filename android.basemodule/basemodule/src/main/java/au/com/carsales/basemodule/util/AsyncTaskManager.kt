package au.com.carsales.basemodule.util

import android.os.AsyncTask


abstract class AsyncTaskManager<T> : AsyncTask<Any, Void, T>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg objects: Any): T? {
        try {
            return doWork(objects)
        } catch (e: Exception) {
            exception = e
        }
        return null
    }

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)

        if (exception == null) {
            onResult(result)
        } else {
            onError()
        }
    }

    @Throws(Exception::class)
    abstract fun doWork(vararg objects: Any): T

    abstract fun onResult(result: T)

    abstract fun onError()
}