package au.com.carsales.basemodule

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import androidx.multidex.MultiDexApplication
import android.util.Log
import au.com.carsales.basemodule.api.volley.VolleyService
import au.com.carsales.basemodule.dependency.baseconcreate.component.BaseConcreteApplicationComponent
import au.com.carsales.basemodule.dependency.baseconcreate.component.DaggerBaseConcreteApplicationComponent
import au.com.carsales.basemodule.dependency.baseconcreate.module.BaseConcreteApplicationModule
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.router.BaseModuleLifeCycleManager
import au.com.carsales.basemodule.router.IModuleConfig
import com.android.volley.toolbox.ImageLoader
import com.facebook.CallbackManager
import com.google.android.gms.analytics.Tracker
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.iid.InstanceID
import com.google.firebase.iid.FirebaseInstanceId

var context: BaseModuleApplication? = null
fun getAppContext(): BaseModuleApplication {
    return context!!
}

abstract class BaseModuleApplication : MultiDexApplication(), LifeCycleDelegate {

    override fun onAppBackgrounded() {
        Log.d("BaseModuleApplication", "onBackground")
    }

    override fun onAppForegrounded() {
        Log.d("BaseModuleApplication", "onForeground")
    }


    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    //todo: use dagger to provide lifeCycleManager
    var lifeCycleManager: BaseModuleLifeCycleManager? = null
    lateinit var volleyService: VolleyService
    abstract fun initLifeCycleManager(): BaseModuleLifeCycleManager
    lateinit var appInstallationId: String

    companion object {
        var tracker: Tracker? = null
        lateinit var mFacebookCallbackManager: CallbackManager
        @SuppressLint("StaticFieldLeak")
        var mGoogleSignInClient: GoogleSignInClient? = null
        //        var membershipSessionServiceData: IMembershipService? = null
//        var authMembershipData: AuthMembershipData? = null
        var tenant: String = String.empty()
        var globalTenant: TenantForAppType = TenantForAppType.DOMESTIC_APP
        var acceptLanguage: String = String.empty()
        var retailApiVersion: String = String.empty()
        var membershipApiVersion: String = String.empty()
        var baseConcreteApplicationComponent: BaseConcreteApplicationComponent? = null
        lateinit var appImageLoader: ImageLoader
    }

    override fun onCreate() {
        super.onCreate()

        val lifeCycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifeCycleHandler)

        context = this
        lifeCycleManager = initLifeCycleManager()
        lifeCycleManager!!.onCreate()

//        val memoryCacheSize: Int
//        val diskCacheSize: Int = 50 * 1024 * 1024
//        val mgr = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val memClass = mgr.memoryClass
//        memoryCacheSize = 1024 * 1024 * memClass / 8
//        volleyService = VolleyService(this, memoryCacheSize, diskCacheSize)
//        appImageLoader = volleyService.imageLoader
//        // Initialize Fabric with the debug-disabled crashlytics.
//      Desactivando firebase
        //appInstallationId = FirebaseInstanceId.getInstance().id

        buildDagger(this);
    }

    override fun onTerminate() {
        super.onTerminate()
        lifeCycleManager?.onDestroy()
    }


    fun getModuleConfig(): IModuleConfig = lifeCycleManager!!.getModuleConfig()
}

enum class TenantForAppType(tenant: String) {

    CHILEAUTOS("chileautos"),
    DEMOTORES("demotores"),
    SOLOAUTOS("soloautos"),
    BIKESALES("bikesales"),
    CARSALES("carsales"),
    AUTOGATE("autogate"),
    DOMESTIC_APP("domesticApp");

    companion object {
        fun from(findValue: String): TenantForAppType = TenantForAppType.values().first { it.name == findValue.toUpperCase() }
    }
}

private fun buildDagger(context: Context): BaseConcreteApplicationComponent {
    if (BaseModuleApplication.baseConcreteApplicationComponent == null) {
        BaseModuleApplication.baseConcreteApplicationComponent = DaggerBaseConcreteApplicationComponent
                .builder()
                .baseConcreteApplicationModule(BaseConcreteApplicationModule(context as BaseModuleApplication))
                .build()
    }
    return BaseModuleApplication.baseConcreteApplicationComponent!!
}

internal fun Context.baseConcreteAppComponent(): BaseConcreteApplicationComponent {
    return buildDagger(this.applicationContext)
}