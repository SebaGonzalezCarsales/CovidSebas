package com.gonzalezcs.covidnewmodule.data.network

import com.gonzalezcs.covidnewmodule.data.model.CovidDateModel

interface CovidDateListener {

    interface CovidDateListener {
        fun onSuccess(covidModel: CovidDateModel?)
        fun onError(error: Throwable)
    }
}