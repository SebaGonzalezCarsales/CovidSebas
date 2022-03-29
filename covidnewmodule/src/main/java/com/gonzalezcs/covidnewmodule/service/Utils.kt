package com.gonzalezcs.covidnewmodule

import android.content.Context
import android.widget.Toast

class Utils {
    fun showToast(text:String,context: Context){
        Toast.makeText(context,text, Toast.LENGTH_SHORT).show()
    }
}