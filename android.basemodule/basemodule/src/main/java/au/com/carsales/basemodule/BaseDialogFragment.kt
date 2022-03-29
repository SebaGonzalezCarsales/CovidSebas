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
import au.com.carsales.basemodule.extension.applyFontForToolbarTitle
import au.com.carsales.basemodule.extension.setNoArrowUpToolbar
import au.com.carsales.basemodule.navigation.BaseNavigationListener
import au.com.carsales.basemodule.util.dialog.BottomSheetDialogManagerListener
import au.com.carsales.basemodule.util.dialog.showSimpleDialog
import au.com.carsales.basemodule.util.layoutManager.WrapContentGridLayoutManager
import au.com.carsales.basemodule.util.layoutManager.WrapContentLinearLayoutManager
import au.com.carsales.basemodule.util.viewModel.BaseViewModel
import au.com.carsales.basemodule.viewcomponent.errorView.GlobalErrorClickHandler
import au.com.carsales.basemodule.widget.loaderRetail.DotLoadingIndicatorView
import com.facebook.login.LoginManager
import com.google.android.material.appbar.CollapsingToolbarLayout

abstract class BaseDialogFragment : DialogFragment(), GlobalError, GlobalErrorClickHandler {

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

    var baseListener: BaseNavigationListener? = null

    var dotLoadingIndicatorView: DotLoadingIndicatorView? = null
    var mResources: Resources? = null
    open val baseViewModel: BaseViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        retainInstance = true
        mResources = resources

        return null
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseNavigationListener) {
            baseListener = context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement BaseNavigationListener"
            )
        }
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
            /*
            Crashlytics.log(
                Log.ERROR,
                BaseDialogFragment::class.simpleName,
                " BaseModuleFragment - showIndicator: " + e.printStackTrace().toString()
            )
            */
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
}