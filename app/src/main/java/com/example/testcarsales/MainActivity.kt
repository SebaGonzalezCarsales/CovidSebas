package com.example.testcarsales

import android.os.Bundle
import android.widget.FrameLayout
import au.com.carsales.basemodule.BaseModuleNavigationActivity
import com.example.testcarsales.ui.StatisticsFragment

class MainActivity : BaseModuleNavigationActivity() {
    override fun layoutContainerId() = R.layout.activity_main
    override fun frameContainer(): FrameLayout = findViewById(R.id.fragmentContainer)

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null)
            pushFragmentRootDetail(StatisticsFragment())
    }

    override fun savedInstanceOfData(outState: Bundle) {}
}