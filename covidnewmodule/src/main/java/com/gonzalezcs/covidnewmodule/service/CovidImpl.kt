package com.gonzalezcs.covidnewmodule.service

import com.gonzalezcs.covidnewmodule.ui.view.CovidDateActivity

class CovidImpl:ICovidNewModuleService {

    override fun getActivity(): CovidDateActivity {
        return CovidDateActivity()
    }

}