package au.com.carsales.basemodule.util.dialog

import androidx.fragment.app.FragmentManager
import au.com.carsales.basemodule.util.checkSafeCommit
import au.com.carsales.basemodule.util.dialog.selectedDialogManager.*

fun showSimpleDialog(title: String?,
                     message: String,
                     callbackOptions: BottomSheetDialogManagerListener,
                     fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceSingleOption(title, message, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showConfirmDialog(title: String?,
                      message: String,
                      callbackOptions: BottomSheetDialogManagerListener,
                      fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceTwoOption(title, message, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showConfirmDialog(title: String?,
                      message: String,
                      confirmButtonTitle: String,
                      callbackOptions: BottomSheetDialogManagerListener,
                      fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceTwoOption(title, message,
            confirmButtonTitle, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showInputDialog(title: String?,
                    suggestedMessage: String?,
                    callbackOptions: BottomSheetDialogManagerListener,
                    fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceInputOption(title, suggestedMessage, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showInputDialog(title: String?,
                    suggestedMessage: String?,
                    typeInput: TypeInputBottomSheet,
                    callbackOptions: BottomSheetDialogManagerListener,
                    fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceInputOption(
            title, suggestedMessage, typeInput, callbackOptions)

    safeDialogShow(fm, builderInner)
}

fun showInputDialog(title: String?,
                    subtitle: String?,
                    message: String?,
                    hint: String?,
                    validations: @ParameterName("Validation <Validation, Error Validation>") Pair<String, String>,
                    typeInput: TypeInputBottomSheet,
                    callbackOptions: BottomSheetDialogManagerListener,
                    fm: FragmentManager) {
    val builderInner = BottomSheetDialogInputManager.newInstance(title,
            subtitle,
            hint,
            message,
            validations,
            typeInput,
            callbackOptions)

    fm.checkSafeCommit {
        builderInner.show(fm, builderInner.tagName())
    }
}

fun showCameraDialog(title: String?,
                     message: String,
                     callbackOptions: BottomSheetDialogManagerListener,
                     fm: FragmentManager) {

    val builderInner = BottomSheetDialogManager.newInstanceCamera(title, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showCameraDialog(title: String?,
                     subtitle: String?,
                     galleryTitle: String?,
                     cameraTitle: String?,
                     callbackOptions: BottomSheetDialogManagerListener,
                     fm: FragmentManager) {

    val builderInner = BottomSheetDialogManager.newInstanceCamera(title,
            subtitle, galleryTitle, cameraTitle, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showListDialog(title: String?,
                   optionList: ArrayList<String>,
                   preSelectedOptionPosition: Int,
                   callbackOptions: BottomSheetDialogManagerListener,
                   fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceOptionListSingleSelection(title, preSelectedOptionPosition, optionList, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showListDialog(title: String?,
                   optionList: ArrayList<String>,
                   callbackOptions: BottomSheetDialogManagerListener,
                   fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceOptionListSingleSelection(title, -1, optionList, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun <T> showSelectedDialog(title: String?,
                           subtitle: String?,
                           optionBottomSheet: List<OptionBottomSheetAdapterBase.OptionsBottomSheetData<T>>,
                           typeDialogSelected: TypeBottomSheetDialogSelected?,
                           callbackOptions: BottomSheetDialogSelectedListener<T>?,
                           fm: FragmentManager) {

    val builderInner = BottomSheetDialogSelectedManager.newInstanceSelection<T>(title, subtitle,
            typeDialogSelected!!)
    builderInner.setDataList(optionBottomSheet, callbackOptions)

    fm.checkSafeCommit {
        builderInner.show(fm, builderInner.tagName())
    }
}

fun showSimpleListDialog(title: String?,
                         optionList: Map<String, String>,
                         callbackOptions: BottomSheetDialogManagerListener,
                         fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceOptionListSingleSelection(
            title, optionList, callbackOptions)
    safeDialogShow(fm, builderInner)
}

fun showSimpleListDialog(title: String?,
                         subtitle: String? = null,
                         optionList: Map<String, String>,
                         callbackOptions: BottomSheetDialogManagerListener,
                         fm: FragmentManager) {
    val builderInner = BottomSheetDialogManager.newInstanceOptionListSingleSelection(
            title, subtitle, optionList, callbackOptions)
    safeDialogShow(fm, builderInner)
}

/**
 * Safe way to show a dialog. It shows the dialog
 * only when the save state of the fragment manager
 * is not saved yet. If this is not implemented,
 * an IllegalStateException can happen
 *
 * @param fm                        The fragment manager
 * @param bottomSheetDialogManager  The bottom sheet dialog manager to show
 */
fun safeDialogShow(fm: FragmentManager,
                   bottomSheetDialogManager: BottomSheetDialogManager) {
    fm.checkSafeCommit {
        bottomSheetDialogManager.show(fm, bottomSheetDialogManager.tagName())
    }
}