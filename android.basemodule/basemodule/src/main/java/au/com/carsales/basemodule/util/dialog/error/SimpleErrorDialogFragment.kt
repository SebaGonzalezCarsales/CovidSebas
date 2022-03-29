package au.com.carsales.basemodule.util.dialog.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import au.com.carsales.basemodule.databinding.DialogGlobalErrorBinding
import au.com.carsales.basemodule.viewcomponent.errorView.APIErrorViewData
import au.com.carsales.basemodule.viewcomponent.errorView.GlobalErrorClickHandler

/**
 * Created by anibalbastias on 2019-06-11.
 * Dialog without Basemodule navigation
 */

class SimpleErrorDialogFragment : DialogFragment() {

    var apiErrorViewData: APIErrorViewData? = null

    // In case this is different from
    // null this methods will be fired
    // instead the default ones
    var errorDialogClickHandler: GlobalErrorClickHandler? = null

    private lateinit var binding: DialogGlobalErrorBinding

    companion object {
        fun newInstance() = SimpleErrorDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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