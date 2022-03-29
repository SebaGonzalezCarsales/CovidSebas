package au.com.carsales.basemodule

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import au.com.carsales.basemodule.baseBottomNavigation.BaseNavigationHelperListener
import au.com.carsales.basemodule.extension.applyFontForToolbarTitle
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.inflate
import au.com.carsales.basemodule.extension.setNoArrowUpToolbar
import au.com.carsales.basemodule.util.dialog.BottomSheetDialogManagerListener
import au.com.carsales.basemodule.util.dialog.showSimpleDialog
import au.com.carsales.basemodule.util.layoutManager.WrapContentGridLayoutManager
import au.com.carsales.basemodule.util.layoutManager.WrapContentLinearLayoutManager
import au.com.carsales.basemodule.util.viewModel.BaseViewModel
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import au.com.carsales.basemodule.viewcomponent.errorView.GlobalErrorClickHandler
import au.com.carsales.basemodule.widget.loaderRetail.DotLoadingIndicatorView
import com.facebook.login.LoginManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.util.crashlytics.CrashlyticsConstants.Companion.CRASHLYTICS_TAG_NAME
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewComponent

abstract class BaseModuleDialogFragment : DialogFragment(), GlobalError, GlobalErrorClickHandler {

    abstract fun tagName(): String
    abstract fun isFullScreen(): Boolean
    abstract fun layoutId(): Int
    abstract fun showLoadingView()
    abstract fun showEmptyView()

    @Deprecated(message = "Use new showGlobalErrorView() instead",
            level = DeprecationLevel.WARNING,
            replaceWith = ReplaceWith(
                    expression = "showGlobalErrorView()",
                    imports = [""]))
    abstract fun showErrorView(message: String?)

    var baseNavigation: BaseNavigationHelperListener? = null
    var tagIntoNavigation: String = String.empty()
    var idNavigation: String = String.empty()
    var idPositionTab: Int = 0
    var dotLoadingIndicatorView: DotLoadingIndicatorView? = null
    var mResources: Resources? = null
    open val baseViewModel: BaseViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addTagNameInfoToCrashlytics()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        mResources = resources

        try {
            if (savedInstanceState != null) {
                idNavigation = savedInstanceState.getString("idNavigation").let { it!! }
                idPositionTab = savedInstanceState.getInt("idPositionTab").let { it }
                tagIntoNavigation = savedInstanceState.getString("tagIntoNavigation").let { it!! }
            }
            return inflater.inflate(layoutId(), container, false)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            /*
            Crashlytics.log(
                    Log.ERROR, "onCreateView", "BaseModuleDialogFragment: "
                    + "layoutId(): " + layoutId() + "idNavigation: " + idNavigation
                    + " idPositionTab: " + idPositionTab.toString()
                    + "StackTrace: " + e.stackTrace.toString()
            )*/
        }
        return null
    }

    /**
     * Adds the tag name (should be
     * the class name) so in next
     * Crash we can see this extra info
     */
    private fun addTagNameInfoToCrashlytics() {
        //Crashlytics.setString(CRASHLYTICS_TAG_NAME, tagName())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("idNavigation", idNavigation)
        outState.putInt("idPositionTab", idPositionTab)
        outState.putString("tagIntoNavigation", tagIntoNavigation)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*if (context is BaseNavigationHelperListener) {
            baseNavigation = context
        } else {
            throw RuntimeException(
                    context.toString()
                            + " must implement BaseBottomNavigationListener"
            )
        }*/
    }

    fun showMessageCloseSessionExpired() {
        showSimpleDialog(
                getString(R.string.close_session_dialog_title),
                getString(R.string.close_session_dialog_subtitle),
                object : BottomSheetDialogManagerListener {
                    override fun onAcceptOption() {
                        LoginManager.getInstance().logOut()
                    }

                    override fun onCancelOption() {

                    }

                    override fun onAcceptAndReturnInputText(message: String) {
                    }
                },
                fragmentManager!!
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (context != null) {
            dotLoadingIndicatorView = DotLoadingIndicatorView(activity!!)
        }
    }

    protected fun showIndicator(rootView: View?, anchorView: View?, inverse: Boolean, dotColor: Int = R.color.white) {
        try {
            if (dotLoadingIndicatorView != null && anchorView != null) {
                if (dotLoadingIndicatorView?.isShown == false) {
                    // Disable rootView for actions
                    setRootView(rootView, false)

                    Handler().postDelayed({
                        dotLoadingIndicatorView?.showOnView(anchorView, inverse)
                        if (mResources != null) {
                            dotLoadingIndicatorView?.changeStartColor(
                                    ResourcesCompat.getColor(
                                            mResources!!,
                                            dotColor,
                                            null
                                    )
                            )
                            dotLoadingIndicatorView?.changeEndColor(
                                    ResourcesCompat.getColor(
                                            mResources!!,
                                            dotColor,
                                            null
                                    )
                            )
                        }
                    }, 300)
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            //Use crashlytics to get more info of the crash
            /*Crashlytics.log(
                    Log.ERROR,
                    BaseModuleDialogFragment::class.simpleName,
                    " BaseModuleFragment - showIndicator: " + e.printStackTrace().toString()
            )*/
        }
    }

    protected fun hideIndicator(rootView: View?) {
        if (dotLoadingIndicatorView != null) {
            // Enable rootView for actions
            setRootView(rootView, true)
            dotLoadingIndicatorView!!.remove()
        }
    }

    protected fun showLoadingDotView(rootView: View?, view: View?, dotColor: Int = R.color.white) {
        // Disable rootView for actions
        setRootView(rootView, false)
        showIndicator(rootView, view, true, dotColor)
    }

    protected fun setRootView(rootView: View?, isEnabled: Boolean) {
        rootView?.isClickable = isEnabled
        rootView?.isEnabled = isEnabled
        rootView?.isFocusable = isEnabled
        rootView?.isFocusableInTouchMode = isEnabled
    }

    fun setupAppBarLayout(
            collapsingToolbar: CollapsingToolbarLayout,
            toolbar: androidx.appcompat.widget.Toolbar,
            toolbarTitle: Int, toolbarColor: Int
    ) {
        // Set Toolbar
        toolbar.title = getString(toolbarTitle)

        // Set Arrow Toolbar
        toolbar.setNoArrowUpToolbar(activity!!)
        toolbar.applyFontForToolbarTitle(activity!!)
        toolbar.setTitleTextColor(ResourcesCompat.getColor(resources, toolbarColor, null))

        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)


        val typeface = ResourcesCompat.getFont(activity!!, R.font.opensans_semibold)
        collapsingToolbar.setCollapsedTitleTypeface(typeface)
        collapsingToolbar.setExpandedTitleTypeface(typeface)

        collapsingToolbar.title = getString(toolbarTitle)
    }

    fun setTopLinearScrollableRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        recyclerView.smoothScrollToPosition(0)
        (recyclerView.layoutManager as WrapContentLinearLayoutManager)
                .scrollToPositionWithOffset(0, 0)
    }

    fun setTopGridScrollableRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        recyclerView.smoothScrollToPosition(0)
        (recyclerView.layoutManager as WrapContentGridLayoutManager)
                .scrollToPositionWithOffset(0, 0)
    }

    override fun onPause() {
        hideIndicator(null)
        super.onPause()
    }

    override fun onDestroy() {
        hideIndicator(null)
        super.onDestroy()
    }

    /**
     * To make a little bit more automatic
     * the APiErrorViewData setting to the
     * APIErrorViewComponent
     *
     * @param apiErrorViewData      the api error view data from the server
     * @param showTryAgain          true to show the try again button, false otherwise
     * @param showClose             true to show the close button, false otherwise
     * @param errorViewComponent    the APIErrorViewComponent view
     * @param onCloseClickAction    the lambda function to be invoked in case the user clicks the close button
     * @param onTryAgainClickAction the lambda function to be invoked in case the user clicks the try again button
     * @param lastCustomCode        optional code to be executed at last
     */
    fun showGlobalErrorView(apiErrorViewData: APIErrorViewData?,
                            showTryAgain: Boolean = true,
                            showClose: Boolean = true,
                            errorViewComponent: APIErrorViewComponent,
                            onCloseClickAction: () -> Unit = {},
                            onTryAgainClickAction: () -> Unit = {},
                            lastCustomCode: () -> Unit = {}) {
        apiErrorViewData?.showTryAgain = showTryAgain
        apiErrorViewData?.showClose = showClose
        errorViewComponent.setData(apiErrorViewData)
        errorViewComponent.setErrorClickHandler(errorClickHandler = object : GlobalErrorClickHandler {
            override fun onCloseClick(view: View, error: APIErrorViewData) {
                onCloseClickAction.invoke()
            }

            override fun onTryAgainClick(view: View, error: APIErrorViewData) {
                onTryAgainClickAction.invoke()
            }
        })

        lastCustomCode.invoke()

    }
}