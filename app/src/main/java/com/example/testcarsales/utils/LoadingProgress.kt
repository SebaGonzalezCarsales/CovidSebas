package com.example.testcarsales.utils

import android.app.Dialog
import android.content.Context
import au.com.carsales.basemodule.context
import com.example.testcarsales.R
import javax.inject.Inject

class LoadingProgress(private val context: Context){
    private var alert: Dialog = Dialog(context)

    init {
        alert.setContentView(R.layout.loading_layout);
        alert.setCancelable(false)
    }

    fun show(status: Boolean = true) {
        if (status)
            alert.show()
        else
            alert.dismiss()
    }
}