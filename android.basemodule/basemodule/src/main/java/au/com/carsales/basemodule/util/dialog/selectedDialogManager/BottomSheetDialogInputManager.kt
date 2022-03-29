package au.com.carsales.basemodule.util.dialog.selectedDialogManager

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.*
import au.com.carsales.basemodule.util.dialog.BottomSheetDialogManagerListener
import au.com.carsales.basemodule.util.isTablet
import kotlinx.android.synthetic.main.content_dialog_buttons.*
import kotlinx.android.synthetic.main.content_dialog_title.*
import kotlinx.android.synthetic.main.fragment_dialog_edit.*
import java.util.regex.Pattern
import java.io.Serializable

class BottomSheetDialogInputManager : BottomSheetDialogFragment() {

    fun tagName(): String = this::class.java.simpleName

    companion object {

        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val MESSAGE = "message"
        const val HINT = "hint"
        const val VALIDATION = "validation"
        const val TYPE_INPUT = "type_input"
        var bottomSheetDialogInputListener: BottomSheetDialogManagerListener? = null

        fun newInstance(title: String?,
                        subtitle: String?,
                        hint: String?,
                        message: String?,
                        validations: Pair<String, String>?,
                        typeInput: TypeInputBottomSheet,
                        InputListener: BottomSheetDialogManagerListener): BottomSheetDialogInputManager {

            val fragment = BottomSheetDialogInputManager()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(MESSAGE, message)
            args.putString(HINT, hint)
            args.putString(SUBTITLE, subtitle)
            args.putSerializable(VALIDATION, validations)
            args.putSerializable(TYPE_INPUT, typeInput)
            fragment.arguments = args
            bottomSheetDialogInputListener = InputListener

            return fragment
        }
    }

    private var title: String? = null
    private var message: String? = null
    private var hint: String? = null
    private var subtitle: String? = null
    private var validationsPair: Pair<String, String>? = null
    private var typeInput: TypeInputBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(TITLE)
            message = it.getString(MESSAGE)
            hint = it.getString(HINT)
            subtitle = it.getString(SUBTITLE)
            validationsPair = it.getSerializable(VALIDATION) as Pair<String, String>?
            typeInput = it.getSerializable(TYPE_INPUT) as TypeInputBottomSheet?
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBase = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialogBase.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            d.setCancelable(false)
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            }

            if (isTablet(context!!)) {
                val width = context!!.resources.getDimensionPixelSize(R.dimen.bottom_sheet_width)
                dialog?.window?.setLayout(if (width > 0) width else ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }

        // Do something with your dialog like setContentView() or whatever
        return dialogBase
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_edit, container)
    }

    private var currentText: String = String.empty()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton.visible()
        acceptButton.disableView()

        titleDialog?.run {
            visible()
            text = title ?: run {
                gone()
                String.empty()
            }
        }

        subTitleDialog?.run {
            visible()
            text = subtitle ?: run {
                gone()
                String.empty()
            }
        }

        editTextDialog?.hint = hint ?: String.empty()
        editTextDialog?.setText(message ?: String.empty())

        //region Filters
        when (typeInput) {
            TypeInputBottomSheet.TEXT -> {
                editTextDialog.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            }
            TypeInputBottomSheet.NUMBER -> {
                editTextDialog.inputType = InputType.TYPE_CLASS_NUMBER
            }

        }

        //endregion

        //region textChanged
        editTextDialog.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable!!.isEmpty()) {
                    acceptButton.disableView()
                } else {
                    acceptButton.enableView()
                }
                currentText = editable.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        //endregion

        cancelButton?.setOnClickListener {
            this.dismiss()
            Handler().postDelayed({

            }, 200)
        }

        acceptButton?.setOnClickListener {
            Handler().postDelayed({
                if (isCorrectInputEdit(currentText)) {
                    this.dismiss()
                    bottomSheetDialogInputListener?.onAcceptAndReturnInputText(currentText)
                } else {
                    Log.e("Error", "" + validationsPair?.second)
                    editTextDialog.error = validationsPair?.second
                }

            }, 200)

        }
    }

    private fun isCorrectInputEdit(currentText: String): Boolean {

        return if (validationsPair != null) {
            val ps = Pattern.compile(validationsPair?.first)
            val ms = ps.matcher(currentText)
            ms.matches()
        } else {
            true
        }
    }
}

enum class TypeInputBottomSheet : Serializable {
    TEXT,
    NUMBER
}