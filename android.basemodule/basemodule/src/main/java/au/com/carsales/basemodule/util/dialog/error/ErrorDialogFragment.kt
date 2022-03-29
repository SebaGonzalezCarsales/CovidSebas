package au.com.carsales.basemodule.util.dialog.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import au.com.carsales.basemodule.BaseModuleDialogFragment
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.databinding.DialogGlobalErrorBinding
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import au.com.carsales.basemodule.viewcomponent.errorView.GlobalErrorClickHandler

/**
 * Created by anibalbastias on 2019-06-11.
 */

class ErrorDialogFragment() : BaseModuleDialogFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun isFullScreen(): Boolean = false
    override fun layoutId(): Int = R.layout.dialog_global_error
    override fun showLoadingView() {}
    override fun showEmptyView() {}
    override fun showErrorView(message: String?) {}

    var apiErrorViewData: APIErrorViewData? = null
    // In case this is different from
    // null this methods will be fired
    // instead the default ones
    var errorDialogClickHandler: GlobalErrorClickHandler? = null

    private lateinit var binding: DialogGlobalErrorBinding

    companion object {
        val ARG_KEY_DATA = "viewData"
        val ARG_KEY_CANCELABLE = "isCancelable"
        fun newInstance() = ErrorDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiErrorViewData = arguments?.getSerializable(ARG_KEY_DATA) as APIErrorViewData?
        isCancelable = arguments?.getSerializable(ARG_KEY_CANCELABLE) as Boolean? ?: true
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Dialog_Alert)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DialogGlobalErrorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorHandler: GlobalErrorClickHandler = object : GlobalErrorClickHandler {
            override fun onCloseClick(view: View, error: APIErrorViewData) {
                dismiss()
                errorDialogClickHandler?.onCloseClick(view, error)
            }

            override fun onTryAgainClick(view: View, error: APIErrorViewData) {
                dismiss()
                errorDialogClickHandler?.onTryAgainClick(view, error)
            }
        }

        // If it's a dialog, set the close button VISIBLE
        apiErrorViewData?.showClose = true

        binding.apiData = apiErrorViewData
        binding.clickHandler = errorHandler
    }
}