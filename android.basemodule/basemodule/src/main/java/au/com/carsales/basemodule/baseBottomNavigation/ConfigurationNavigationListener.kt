package au.com.carsales.basemodule.baseBottomNavigation

import android.os.Bundle

interface ConfigurationNavigationListener {

    fun initView(savedInstanceState: Bundle?)
    fun savedInstanceOfData(outState: Bundle)
    fun currentChangeTab(currentTab: String, positionTab: Int) {}
}