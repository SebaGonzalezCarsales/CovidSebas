package com.example.testcarsales

import android.content.Context
import androidx.fragment.app.Fragment
import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import com.example.testcarsales.di.base.DaggerPresentationComponent
import com.example.testcarsales.di.base.PresentationApplicationModule
import com.example.testcarsales.di.base.PresentationComponent

open class MainApplication : BaseModuleApplication() {
    companion object {
        var applicationComponent: PresentationComponent? = null
    }

    override fun initLifeCycleManager(): BaseModuleLifeCycleManager {
        return ModuleLifeCycleManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }
}

fun Context.appComponent(): PresentationComponent {
    return buildDagger(this.applicationContext)
}

fun Fragment.appComponent(): PresentationComponent {
    return buildDagger(this.requireContext().applicationContext)
}

fun buildDagger(context: Context): PresentationComponent {
    if (MainApplication.applicationComponent == null) {

        MainApplication.applicationComponent = DaggerPresentationComponent
            .builder()
            .presentationApplicationModule(PresentationApplicationModule(context as MainApplication))
            .build()
    }
    return MainApplication.applicationComponent!!
}