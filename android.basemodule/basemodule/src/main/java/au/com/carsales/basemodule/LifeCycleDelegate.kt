package au.com.carsales.basemodule

interface LifeCycleDelegate {
    fun onAppBackgrounded()
    fun onAppForegrounded()
}