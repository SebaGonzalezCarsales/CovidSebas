package au.com.carsales.basemodule.util.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.empty
import au.com.carsales.basemodule.extension.gone
import au.com.carsales.basemodule.extension.visible
import au.com.carsales.basemodule.util.dialog.selectedDialogManager.TypeInputBottomSheet
import au.com.carsales.basemodule.util.isTablet
import au.com.carsales.basemodule.widget.SimpleDividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.content_dialog_buttons.*
import kotlinx.android.synthetic.main.content_dialog_title.*
import kotlinx.android.synthetic.main.fragment_dialog_camera.*
import kotlinx.android.synthetic.main.fragment_dialog_edit.*
import kotlinx.android.synthetic.main.fragment_dialog_list.*
import kotlinx.android.synthetic.main.fragment_dialog_message.*
import java.io.Serializable


enum class OptionDialogManager : Serializable {
    SINGLE_OPTION,
    TWO_OPTION,
    INPUT,
    CAMERA,
    LIST_OPTIONS_SINGLE_SELECTION,
    LIST_OPTIONS_SINGLE_SELECTION_WITHOUT_RADIO_BUTTON
}

class BottomSheetDialogManager : BottomSheetDialogFragment() {

    fun tagName(): String = this::class.java.simpleName

    companion object {

        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val GALLERY_TITLE = "galleryTitle"
        const val CAMERA_TITLE = "cameraTitle"
        const val MESSAGE = "message"
        const val CONFIRM_BUTTON = "confirmButton"
        const val OPTION = "option"
        const val TYPE_INPUT = "typeInput"
        const val OPTION_PRE_SELECTED_POSITION = "optionPreSelectedPosition"
        const val OPTION_LIST = "optionList"
        const val OPTION_MAP = "optionMap"
        var bottomSheetDialogManagerListener: BottomSheetDialogManagerListener? = null

        fun newInstanceSingleOption(title: String?,
                                    message: String?,
                                    callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putSerializable(OPTION, OptionDialogManager.SINGLE_OPTION)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceTwoOption(title: String?,
                                 message: String?,
                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putSerializable(OPTION, OptionDialogManager.TWO_OPTION)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceTwoOption(title: String?,
                                 message: String?,
                                 confirmButtonTitle: String?,
                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putString(CONFIRM_BUTTON, confirmButtonTitle)
            args.putSerializable(OPTION, OptionDialogManager.TWO_OPTION)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceInputOption(title: String?,
                                   message: String?,
                                   callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putSerializable(OPTION, OptionDialogManager.INPUT)
            args.putSerializable(TYPE_INPUT, TypeInputBottomSheet.TEXT)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceInputOption(title: String?,
                                   message: String?,
                                   typeInput: TypeInputBottomSheet,
                                   callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putSerializable(OPTION, OptionDialogManager.INPUT)
            args.putSerializable(TYPE_INPUT, typeInput)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceCamera(title: String?, callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putSerializable(OPTION, OptionDialogManager.CAMERA)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceCamera(title: String?,
                              subtitle: String?,
                              galleryTitle: String?,
                              cameraTitle: String?,
                              callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()

            args.putString(TITLE, title)
            args.putString(SUBTITLE, subtitle)
            args.putString(GALLERY_TITLE, galleryTitle)
            args.putString(CAMERA_TITLE, cameraTitle)
            args.putSerializable(OPTION, OptionDialogManager.CAMERA)

            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceOptionListSingleSelection(title: String?,
                                                 optionPreSelectedPosition: Int,
                                                 optionList: ArrayList<String> = arrayListOf(),
                                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putInt(OPTION_PRE_SELECTED_POSITION, optionPreSelectedPosition)
            args.putStringArrayList(OPTION_LIST, optionList)
            args.putSerializable(OPTION, OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceOptionListSingleSelection(title: String?,
                                                 subtitle: String?,
                                                 optionMap: Map<String, Any> = mapOf(),
                                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(SUBTITLE, subtitle)
            args.putSerializable(OPTION_MAP, optionMap as Serializable)
            args.putSerializable(OPTION, OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION_WITHOUT_RADIO_BUTTON)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceOptionListSingleSelection(title: String?,
                                                 optionMap: Map<String, Any> = mapOf(),
                                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putSerializable(OPTION_MAP, optionMap as Serializable)
            args.putSerializable(OPTION, OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION_WITHOUT_RADIO_BUTTON)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }

        fun newInstanceOptionListSingleSelection(title: String?,
                                                 message: String?,
                                                 optionPreSelectedPosition: Int,
                                                 optionList: ArrayList<String> = arrayListOf(),
                                                 callbackOptions: BottomSheetDialogManagerListener): BottomSheetDialogManager {

            val fragment = BottomSheetDialogManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putInt(OPTION_PRE_SELECTED_POSITION, optionPreSelectedPosition)
            args.putStringArrayList(OPTION_LIST, optionList)
            args.putSerializable(OPTION, OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION)
            fragment.arguments = args
            bottomSheetDialogManagerListener = callbackOptions
            return fragment
        }
    }

    private var title: String? = null
    private var subtitle: String? = null
    private var galleryTitle: String? = null
    private var cameraTitle: String? = null
    private var message: String? = null
    private var acceptButtonMessage: String? = null
    private var optionShowing: OptionDialogManager = OptionDialogManager.SINGLE_OPTION
    private var optionList: ArrayList<String> = arrayListOf()
    private var optionMap: Map<String, Any> = mapOf()
    private var optionPreSelected: Int = 0
    private var optionMapAdapter: OptionMapAdapter? = null
    private var optionListAdapter: OptionListAdapter? = null
    private var typeInput: TypeInputBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(TITLE)
            subtitle = it.getString(SUBTITLE)
            galleryTitle = it.getString(GALLERY_TITLE)
            cameraTitle = it.getString(CAMERA_TITLE)
            message = it.getString(MESSAGE)
            typeInput = it.getSerializable(TYPE_INPUT) as TypeInputBottomSheet?
            acceptButtonMessage = it.getString(CONFIRM_BUTTON, "Done")
            optionShowing = it.getSerializable(OPTION) as OptionDialogManager
            optionList = it.getStringArrayList(OPTION_LIST) as? ArrayList<String> ?: arrayListOf()
            optionPreSelected = it.getInt(OPTION_PRE_SELECTED_POSITION, 0)
            optionMap = it.getSerializable(OPTION_MAP) as? Map<String, Any> ?: mapOf()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBase = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialogBase.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            d.setCancelable(false)
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from(bottomSheet)?.state = BottomSheetBehavior.STATE_EXPANDED

            context?.let {
                if (isTablet(it)) {
                    val width = context?.resources?.getDimensionPixelSize(R.dimen.bottom_sheet_width)

                    width?.let {
                        dialog.window?.setLayout(if (it > 0) it else ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                    }
                }
            }
        }

        // Do something with your dialog like setContentView() or whatever
        return dialogBase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return when (optionShowing) {
            OptionDialogManager.INPUT ->
                inflater.inflate(R.layout.fragment_dialog_edit, container)
            OptionDialogManager.CAMERA ->
                inflater.inflate(R.layout.fragment_dialog_camera, container)
            OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION, OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION_WITHOUT_RADIO_BUTTON ->
                inflater.inflate(R.layout.fragment_dialog_list, container)
            else -> inflater.inflate(R.layout.fragment_dialog_message, container)
        }
    }

    private fun setHeaderSettings() {
        title?.let {
            titleDialog?.run {
                visible()
                text = title
            }
        }

        subtitle?.let {
            subTitleDialog?.run {
                visible()
                text = subtitle
            }
        }
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Default layout
        if (title.isNullOrEmpty()) {
            titleDialog?.gone()
        } else {
            titleDialog?.visible()
            titleDialog?.text = title
        }

        if (message.isNullOrEmpty()) {
            messageDialog?.gone()
        } else {
            messageDialog?.visible()
            messageDialog?.text = message
            messageDialog?.movementMethod = ScrollingMovementMethod()
        }

        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog?.dismiss()
            }
            true
        }

        when (optionShowing) {
            OptionDialogManager.SINGLE_OPTION -> {
                cancelButton.gone()
                messageDialog.text = message

                acceptButton?.setOnClickListener {
                    this.dismiss()
                    bottomSheetDialogManagerListener?.onAcceptOption()
                }
            }
            OptionDialogManager.TWO_OPTION -> {
                cancelButton.visible()
                messageDialog.text = message
                acceptButton.text = acceptButtonMessage

                acceptButton?.setOnClickListener {
                    this.dismiss()
                    bottomSheetDialogManagerListener?.onAcceptOption()
                }
            }
            OptionDialogManager.INPUT -> {
                cancelButton.visible()
                setHeaderSettings()

                when (typeInput) {
                    TypeInputBottomSheet.TEXT -> {
                        editTextDialog.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

                        editTextDialog.filters = arrayOf(InputFilter { src, _, _, _, _, _ ->
                            if (src == "") { // for backspace
                                return@InputFilter src
                            }
                            if (src.toString().matches("[a-zA-Záéíóúüñöç 0-9\\-.,:_&/!$%]+".toRegex())) {
                                src
                            } else ""
                        }, InputFilter.LengthFilter(50))
                    }
                    TypeInputBottomSheet.NUMBER -> {
                        editTextDialog.inputType = InputType.TYPE_CLASS_NUMBER

                        editTextDialog.filters = arrayOf(InputFilter { src, _, _, _, _, _ ->
                            if (src == "") { // for backspace
                                return@InputFilter src
                            } else {
                                ""
                            }
                        }, InputFilter.LengthFilter(10))
                    }
                }

                try {
                    editTextDialog.text = SpannableStringBuilder(message ?: String.empty())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                acceptButton?.setOnClickListener {
                    this.dismiss()
                    bottomSheetDialogManagerListener?.onAcceptAndReturnInputText(editTextDialog.text.toString())
                }
            }
            OptionDialogManager.CAMERA -> {
                acceptButton.gone()
                setHeaderSettings()

                galleryTitle?.let {
                    galleryButton.text = it
                }

                cameraTitle?.let {
                    cameraButton.text = it
                }

                galleryButton.setOnClickListener {
                    this.dismiss()
                    Handler().postDelayed({
                        bottomSheetDialogManagerListener?.onGalleryOption()
                    }, 200)

                }

                cameraButton.setOnClickListener {
                    this.dismiss()
                    Handler().postDelayed({
                        bottomSheetDialogManagerListener?.onCameraOption()
                    }, 200)
                }
            }
            OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION -> {
                cancelButton.visible()
                acceptButton.gone()

                setHeaderSettings()

                optionListAdapter = OptionListAdapter(optionList, optionPreSelected)
                optionListAdapter?.actionListener = object : OptionListAdapter.OptionListAdapterListener {
                    override fun onItemPressed(item: String) {
                        this@BottomSheetDialogManager.dismiss()
                        bottomSheetDialogManagerListener?.onOptionItemSelected(item)
                    }
                }

                recyclerViewDialog.adapter = optionListAdapter
                recyclerViewDialog.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                recyclerViewDialog.addItemDecoration(SimpleDividerItemDecoration(activity!!))

            }
            OptionDialogManager.LIST_OPTIONS_SINGLE_SELECTION_WITHOUT_RADIO_BUTTON -> {
                cancelButton.visible()
                acceptButton.gone()

                setHeaderSettings()

                optionMapAdapter = OptionMapAdapter(optionMap)
                optionMapAdapter?.actionListener = object : OptionMapAdapter.OptionMapAdapterListener {
                    override fun onItemSelected(item: Any) {
                        this@BottomSheetDialogManager.dismiss()
                        bottomSheetDialogManagerListener?.onOptionItemSelected(item.toString())
                    }
                }

                recyclerViewDialog.adapter = optionMapAdapter
                recyclerViewDialog.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                recyclerViewDialog.addItemDecoration(SimpleDividerItemDecoration(activity!!))


            }
        }

        // Cancel button action for all dialogs
        cancelButton?.setOnClickListener {
            this.dismiss()
            Handler().postDelayed({
                bottomSheetDialogManagerListener?.onCancelOption()
            }, 200)
        }
    }
}


interface BottomSheetDialogManagerListener {
    fun onAcceptOption() {}
    fun onCancelOption() {}
    fun onAcceptAndReturnInputText(message: String) {}
    fun onGalleryOption() {}
    fun onCameraOption() {}
    fun onOptionItemSelected(option: String?) {}
}