package au.com.carsales.basemodule

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import au.com.carsales.basemodule.baseBottomNavigation.BaseNavigationHelper
import au.com.carsales.basemodule.baseBottomNavigation.BaseNavigationHelperListener
import au.com.carsales.basemodule.baseBottomNavigation.ConfigurationNavigationListener
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.lastElement
import au.com.carsales.basemodule.extension.pop
import au.com.carsales.basemodule.util.crashlytics.CrashlyticsConstants.Companion.CRASHLYTICS_TAG_NAME
import au.com.carsales.basemodule.util.getShortEdgeDisplayPixels
import au.com.carsales.basemodule.util.isTablet
import java.util.*


abstract class BaseModuleActivity : AppCompatActivity(),
        BaseNavigationHelperListener,
        ConfigurationNavigationListener {

    abstract fun layoutId(): Int

    var mStacksFragment: HashMap<String, MutableList<String>> = hashMapOf()
    var currentTab = String.empty()
    var baseNavigationHelper: BaseNavigationHelper? = null

    // Var for resize activity scrolling in tablet mode
    private var usableHeightPrevious: Int = 0
    private var mChildOfContent: View? = null
    private var frameLayoutParams: FrameLayout.LayoutParams? = null


    open var fragEnterAnim: Int = R.anim.fade_in_fragment
    open var fragExitAnim: Int = R.anim.fade_out_fragment
    open var fragPopEnterAnim: Int = R.anim.fade_in_fragment
    open var fragPopExitAnim: Int = R.anim.fade_out_fragment

    init {
        baseNavigationHelper = BaseNavigationHelper(supportFragmentManager)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        addClassNameInfoToCrashlytics()

        if (savedInstanceState != null) {
            mStacksFragment = savedInstanceState.getSerializable("data") as HashMap<String, MutableList<String>>
            currentTab = savedInstanceState.getString("currentTab") ?: String.empty()
        }
        initView(savedInstanceState)
        baseNavigationHelper?.run {
            enterAnim = fragEnterAnim
            exitAnim = fragExitAnim
            popEnterAnim = fragPopEnterAnim
            popExitAnim = fragPopExitAnim
        }
    }

    /**
     * Adds the class name so in next
     * Crashlytics report we can see
     * this extra info
     */
    private fun addClassNameInfoToCrashlytics() {
       // Crashlytics.setString(CRASHLYTICS_TAG_NAME, this::class.java.simpleName)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("data", mStacksFragment)
        outState?.putSerializable("currentTab", currentTab)
        savedInstanceOfData(outState)
    }

    open fun resizeIfNeeded(isTablet: Boolean) {
        if (isTablet) {
            resize()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun resize() {
        val originalWidth = this.resources.displayMetrics.widthPixels
        val originalHeight = this.resources.displayMetrics.heightPixels
        val isPortrait = this.resources.configuration.orientation == 1
        val width: Int
        val height: Int
        val widthMultiplier: Float
        if (isPortrait) {
            widthMultiplier = 0.9f
            width = (originalWidth.toFloat() * widthMultiplier).toInt()
            height = (originalHeight.toFloat() * 0.85f).toInt()
        } else {
            widthMultiplier = 0.9f
            height = (originalHeight.toFloat() * widthMultiplier).toInt()
            width = (height.toFloat() / (1 / widthMultiplier)).toInt()
        }
        this.window.setLayout(width, height)
    }

    open fun resizeLayoutParamsIfNeeded(isTablet: Boolean, layoutResId: Int) {
        if (isTablet) {
            resizeLayoutParams(layoutResId)
        }
    }

    private fun resizeLayoutParams(layoutResId: Int) {
        val params = findViewById<View>(layoutResId).layoutParams
        var containerWidth = -1
        var containerHeight = -1
        if (containerHeight < 0) {
            //val originalWidth = this.resources.displayMetrics.widthPixels
            val originalHeight = this.resources.displayMetrics.heightPixels
            val isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            val displayShortEdge = getShortEdgeDisplayPixels(this)

            var actionBarHeight: Int = 0
            val tv = TypedValue()

            if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            }

            val multiplier = 0.85f//0.85/4/3=0.6375, to keep consistent with hmc
            containerWidth = ((displayShortEdge * multiplier) / (4 / 3f)).toInt()
            if (isPortrait) {
                containerHeight = (containerWidth.toFloat() / (3 / 4f)).toInt()
            } else {
                containerHeight = ((originalHeight - actionBarHeight) * multiplier).toInt()
            }


        }
        params.width = containerWidth
        params.height = containerHeight

    }


    fun getLastFragmentNotNull(list: List<androidx.fragment.app.Fragment>): androidx.fragment.app.Fragment? =
            list.indices
                    .reversed()
                    .map { list[it] }
                    .firstOrNull()

    fun enableResizeWindowForFloatingWindow() {
        try {
            val content = findViewById<View>(android.R.id.content) as FrameLayout
            mChildOfContent = content.getChildAt(0)
            mChildOfContent?.viewTreeObserver?.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
            frameLayoutParams = mChildOfContent?.layoutParams as FrameLayout.LayoutParams
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent?.rootView?.height!!
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams?.height = usableHeightSansKeyboard - heightDifference
            } else {
                // keyboard probably just became hidden
                if (isTablet(this))
                    frameLayoutParams?.height = usableHeightSansKeyboard
                else
                    frameLayoutParams?.height = usableHeightNow
            }
            mChildOfContent!!.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent?.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

    override fun lasElemetofStack(): String {
        return mStacksFragment[currentTab]?.lastElement()!!
    }

    override fun popFromStack() {
        mStacksFragment[currentTab]!!.pop()
    }

    override fun getListBaseFragment(): List<BaseModuleFragment> {
        return baseNavigationHelper!!.getBaseFragments()
    }

    override fun getInstanceFragmentInStack(fragment: BaseModuleFragment): BaseModuleFragment? {
        getListBaseFragment().forEach {
            if (it.tagName() == fragment.tagName()) {
                return it
            }
        }

        return null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig)
        //setContentView(layoutId())
    }
}
