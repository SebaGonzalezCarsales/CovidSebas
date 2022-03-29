package au.com.carsales.basemodule.util.dialog.selectedDialogManager

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.disableView
import au.com.carsales.basemodule.extension.enableView
import au.com.carsales.basemodule.extension.gone
import au.com.carsales.basemodule.extension.visible
import au.com.carsales.basemodule.util.isTablet
import au.com.carsales.basemodule.widget.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.content_dialog_buttons.*
import kotlinx.android.synthetic.main.content_dialog_title.*
import kotlinx.android.synthetic.main.fragment_dialog_list.*

class BottomSheetDialogSelectedManager<T> : BottomSheetDialogFragment(), OptionSheetAdapterListener {

    fun tagName(): String = this::class.java.simpleName

    companion object {

        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val OPTION_DIALOG = "option_dialog"
        const val OPTION_MAP = "optionMap"

        fun <T> newInstanceSelection(title: String?,
                                     subtitle: String?,
                                     typeDialogSelected: TypeBottomSheetDialogSelected): BottomSheetDialogSelectedManager<T> {

            val fragment = BottomSheetDialogSelectedManager<T>()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putString(SUBTITLE, subtitle)
            args.putSerializable(OPTION_DIALOG, typeDialogSelected)
            fragment.arguments = args
            return fragment
        }
    }

    private var title: String? = null
    private var subtitle: String? = null
    private var optionDialog: TypeBottomSheetDialogSelected? = null
    private var listOptionData: List<OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>> = mutableListOf()
    private var bottomSheetSelectedListener: BottomSheetDialogSelectedListener<T>? = null
    fun setDataList(optionBottomSheet: List<OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>>,
                    bottomSheetSelectedListener: BottomSheetDialogSelectedListener<T>?) {
        listOptionData = optionBottomSheet
        this.bottomSheetSelectedListener = bottomSheetSelectedListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(TITLE)
            subtitle = it.getString(SUBTITLE)
            optionDialog = it.getSerializable(OPTION_DIALOG) as? TypeBottomSheetDialogSelected?
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBase = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialogBase.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            d.setCancelable(false)
            var bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
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
        return inflater.inflate(R.layout.fragment_dialog_list, container)

    }

    private var optionMapAdapter: BottomSheetDialogManagerAdapter<T>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurationForType()

        optionMapAdapter = BottomSheetDialogManagerAdapter(optionDialog!!, listOptionData, this)
        recyclerViewDialog.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity!!, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        recyclerViewDialog.addItemDecoration(SimpleDividerItemDecoration(activity!!))
        recyclerViewDialog.adapter = optionMapAdapter

        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog?.dismiss()
            }
            true
        }

        cancelButton?.setOnClickListener {
            this.dismiss()
            Handler().postDelayed({

            }, 200)
        }

        acceptButton?.setOnClickListener { view1 ->
            this.dismiss()
            Handler().postDelayed({

                when (optionDialog) {
                    TypeBottomSheetDialogSelected.SINGLE_SELECTION_WITH_CHECK -> {
                        val dataSelected = optionMapAdapter!!.items.first { it.isSelected }.dataOption
                        bottomSheetSelectedListener?.onAcceptSingleOption(dataSelected)
                    }
                    TypeBottomSheetDialogSelected.MULTIPLE_SELECTION_WITH_CHECK -> {
                        val dataListSelected = optionMapAdapter!!.items.filter { it.isSelected }.map { it.dataOption }
                        bottomSheetSelectedListener?.onAcceptMultipleOption(dataListSelected)
                    }
                }

            }, 200)

        }


    }

    private fun configurationForType() {

        titleDialog.text = title ?: ""
        subTitleDialog.text = subtitle ?: ""

        when (optionDialog) {
            TypeBottomSheetDialogSelected.SINGLE_SELECTION_WITH_CHECK,
            TypeBottomSheetDialogSelected.MULTIPLE_SELECTION_WITH_CHECK -> {
                cancelButton.visible()
                acceptButton.disableView()
            }
            TypeBottomSheetDialogSelected.DISPLAY_DATA -> {
                cancelButton.gone()
                acceptButton.visible()
            }
        }
    }

    override fun changeSingleAdapterData() {
        acceptButton.enableView()
    }

    override fun changeMultipleAdapterData(isEnable: Boolean) {
        if (isEnable) {
            acceptButton.enableView()
        } else {
            acceptButton.disableView()
        }
    }
}

interface BottomSheetDialogSelectedListener<T> {
    fun onAcceptSingleOption(selected: T) {}
    fun onAcceptMultipleOption(listSelected: List<T>) {}
}


//interface BottomSheetDialogManagerListener {
//    fun onAcceptOption() {}
//    fun onCancelOption() {}
//    fun onAcceptAndReturnInputText(message: String) {}
//    fun onGalleryOption() {}
//    fun onCameraOption() {}
//    fun onOptionItemSelected(option: Any) {}
//}

enum class TypeBottomSheetDialogSelected {

    SINGLE_SELECTION_WITH_CHECK,
    MULTIPLE_SELECTION_WITH_CHECK,
    DISPLAY_DATA
}