package au.com.carsales.basemodule.util.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IntDef

import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.gone
import au.com.carsales.basemodule.util.isTablet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_attachment.*

/*
* to make the callback instance work, BottomSheetDialogFragment need talk with his caller fragment.
* parentFragment is null for DialogFragment
* use targetFragment to achieve the purpose.
* so caller fragment need set itsself to be the targetFragment
* */

class AttachmentBottomSheetDialogFragment : BottomSheetDialogFragment() {
    fun tagName(): String = this::class.java.simpleName
    private var attachmentBottomSheetListener: AttachmentBottomSheetListener? = null
    private var optionGallery: Int = OPTION_DISABLED
    private var optionCamera: Int = OPTION_DISABLED
    private var optionDocument: Int = OPTION_DISABLED

    @IntDef(TYPE_MEDIA_PHOTO, TYPE_MEDIA_VIDEO, TYPE_ALL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class AttachmentType


    companion object {

        /** Media Type: Photo*/
        const val TYPE_MEDIA_PHOTO: Int = 0x01
        /** Media Type: Video*/
        const val TYPE_MEDIA_VIDEO: Int = 0x02
        /** Type: All*/
        const val TYPE_ALL: Int = 0xFF

        /** option disabled*/
        private const val OPTION_DISABLED: Int = 0x000
        /** option enabled*/
        private const val OPTION_ENABLED: Int = 0x100

        private const val TYPE_MASK = 0x0FF
        private const val OPTION_MASK = 0xF00

        private const val ARG_OPTION_GALLERY: String = "arg_option_gallery"
        private const val ARG_OPTION_CAMERA: String = "arg_option_camera"
        private const val ARG_OPTION_DOCUMENT: String = "arg_option_document"
        /**
         *
         * Various behavioral options and settings. all 3 options are enabled by default
         * <code>
         *    newInstance()
         *    newInstance(Pair(true, TYPE_MEDIA_PHOTO), Pair(false, TYPE_MEDIA_PHOTO), Pair(true, TYPE_ALL)
         * </code>
         * @param gallerySetting first value true - gallery option enabled, otherwise disabled. second value: attachment type
         * @param cameraSetting first value true - camera option enabled, otherwise disabled. second value: attachment type
         * @param documentSetting first value true - document option enabled, otherwise disabled. second value: attachment type
         * @see TYPE_MEDIA_PHOTO
         * @see TYPE_MEDIA_VIDEO
         * @see TYPE_ALL
         *
         */
        fun newInstance(gallerySetting: Pair<Boolean, Int> = Pair(true, TYPE_ALL),
                        cameraSetting: Pair<Boolean, Int> = Pair(true, TYPE_ALL),
                        documentSetting: Pair<Boolean, Int> = Pair(true, TYPE_ALL)): AttachmentBottomSheetDialogFragment {
            val fragment = AttachmentBottomSheetDialogFragment()
            val args = Bundle()


            val gallery = if (gallerySetting.first) {
                OPTION_ENABLED or gallerySetting.second
            } else {
                OPTION_DISABLED or gallerySetting.second
            }

            val camera = if (cameraSetting.first) {
                OPTION_ENABLED or cameraSetting.second
            } else {
                OPTION_DISABLED or cameraSetting.second
            }

            val document = if (documentSetting.first) {
                OPTION_ENABLED or documentSetting.second
            } else {
                OPTION_DISABLED or documentSetting.second
            }


            args.putInt(ARG_OPTION_GALLERY, gallery)
            args.putInt(ARG_OPTION_CAMERA, camera)
            args.putInt(ARG_OPTION_DOCUMENT, document)


            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        optionGallery = arguments?.getInt(ARG_OPTION_GALLERY) ?: OPTION_DISABLED
        optionCamera = arguments?.getInt(ARG_OPTION_CAMERA) ?: OPTION_DISABLED
        optionDocument = arguments?.getInt(ARG_OPTION_DOCUMENT) ?: OPTION_DISABLED

        val dialogBase = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialogBase.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
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
        if (targetFragment is AttachmentBottomSheetListener)
            attachmentBottomSheetListener = targetFragment as AttachmentBottomSheetListener

        return inflater.inflate(R.layout.fragment_bottom_sheet_attachment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewCamera.setOnClickListener {
            this.dismiss()
            attachmentBottomSheetListener?.onCameraSelected(getAttachmentType(optionCamera))
        }
        imageViewGallery.setOnClickListener {
            this.dismiss()
            attachmentBottomSheetListener?.onGallerySelected(getAttachmentType(optionGallery))
        }
        imageViewDocument.setOnClickListener {
            this.dismiss()
            attachmentBottomSheetListener?.onDocumentSelected(getAttachmentType(optionDocument))
        }

        if (!isCameraOptionEnabled()) {
            imageViewCamera.gone()
            textViewCamera.gone()
        }
        if (!isGalleryOptionEnabled()) {
            imageViewGallery.gone()
            textViewGallery.gone()
        }
        if (!isDocumentOptionEnabled()) {
            imageViewDocument.gone()
            textViewDocument.gone()
        }


    }

    private fun isGalleryOptionEnabled(): Boolean = (optionGallery and OPTION_MASK) == OPTION_ENABLED
    private fun isCameraOptionEnabled(): Boolean = (optionCamera and OPTION_MASK) == OPTION_ENABLED
    private fun isDocumentOptionEnabled(): Boolean = (optionDocument and OPTION_MASK) == OPTION_ENABLED

    @AttachmentType
    private fun getAttachmentType(optionsFlag: Int): Int {
        return when ((optionsFlag and TYPE_MASK)) {
            TYPE_MEDIA_PHOTO -> {
                TYPE_MEDIA_PHOTO
            }
            TYPE_MEDIA_VIDEO -> {
                TYPE_MEDIA_VIDEO
            }
            TYPE_ALL -> {
                TYPE_ALL
            }
            else -> {
                TYPE_ALL
            }
        }
    }

}


interface AttachmentBottomSheetListener {
    fun onDocumentSelected(@AttachmentBottomSheetDialogFragment.AttachmentType fileType: Int)
    fun onCameraSelected(@AttachmentBottomSheetDialogFragment.AttachmentType mediaType: Int)
    fun onGallerySelected(@AttachmentBottomSheetDialogFragment.AttachmentType mediaType: Int)
}

