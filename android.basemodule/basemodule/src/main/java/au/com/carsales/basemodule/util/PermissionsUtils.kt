package au.com.carsales.basemodule.util

import android.Manifest
import android.app.Activity
import android.widget.Toast
import au.com.carsales.basemodule.R
import au.com.carsales.basemodule.extension.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionsUtils {
    companion object {
        val CAMERA_PERMISSION: String = Manifest.permission.CAMERA
        val READ_EXTERNAL_STORAGE_PERMISSION: String = Manifest.permission.READ_EXTERNAL_STORAGE
        val WRITE_EXTERNAL_STORAGE_PERMISSION: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val RECORD_AUDIO_PERMISSION: String = Manifest.permission.RECORD_AUDIO
        val ACCESS_NETWORK_STATE_PERMISSION: String = Manifest.permission.ACCESS_NETWORK_STATE
        val MODIFY_AUDIO_SETTINGS_PERMISSION: String = Manifest.permission.MODIFY_AUDIO_SETTINGS
        val DISABLE_KEYGUARD_PERMISSION: String = Manifest.permission.DISABLE_KEYGUARD
        val GET_ACCOUNTS_PERMISSION: String = Manifest.permission.GET_ACCOUNTS
    }
}

fun Activity.checkPermissionsFor(
        permissionsList: List<String>,
        onPermissionGrantedUnit: (() -> Unit)? = null,
        onPermissionDeniedUnit: ((MutableList<PermissionDeniedResponse>) -> Unit)? = null,
        onPermissionPermanentlyDeniedUnit: ((MutableList<PermissionDeniedResponse>) -> Unit)? = null,
        continuePermissionRequest: Boolean = true,
        showDefaultMessage: Boolean = false
) {

    Dexter.withActivity(this)
            .withPermissions(permissionsList)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.run {
                        if (areAllPermissionsGranted()) {
                            onPermissionGrantedUnit?.invoke()
                        } else {
                            if (isAnyPermissionPermanentlyDenied) {
                                if (onPermissionPermanentlyDeniedUnit == null) {
                                    toast(getString(R.string.feedback_permissions_permanentlty_denied), Toast.LENGTH_LONG)

                                    /*
                                        If there is any permission permanently denied then open app
                                        settings to grant the permission(s) manually as the default
                                        action
                                    */
                                    openAppSettings()
                                } else {
                                    if (showDefaultMessage) {
                                        toast(getString(R.string.feedback_permissions_permanentlty_denied), Toast.LENGTH_LONG)
                                    }

                                    onPermissionPermanentlyDeniedUnit.invoke(report.deniedPermissionResponses)
                                }
                            } else {
                                if (onPermissionDeniedUnit == null) {
                                    toast(getString(R.string.feedback_permissions_denied), Toast.LENGTH_LONG)
                                } else {
                                    if (showDefaultMessage) {
                                        toast(getString(R.string.feedback_permissions_denied), Toast.LENGTH_LONG)
                                    }

                                    onPermissionDeniedUnit.invoke(report.deniedPermissionResponses)
                                }
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    token?.let {
                        if (continuePermissionRequest) {
                            it.continuePermissionRequest()
                        } else {
                            it.cancelPermissionRequest()
                        }
                    }
                }
            })
            .onSameThread()
            .check()
}