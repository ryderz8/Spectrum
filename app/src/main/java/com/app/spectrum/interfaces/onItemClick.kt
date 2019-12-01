package com.app.spectrum.interfaces

import com.app.spectrum.model.ClickEnum
import com.app.spectrum.model.CompanyDataModel

/**
 * Created by amresh on 30/11/2019
 */
interface onItemClick {
    fun onClick(companyDataModel: CompanyDataModel, clickType : ClickEnum)
}

