package com.app.spectrum.interfaces

import com.app.spectrum.model.ClickEnum
import com.app.spectrum.model.MemberDataModel

/**
 * Created by amresh on 01/12/2019
 */
interface OnMemberClick {
    fun onMemberClick(memberDataModel: MemberDataModel, clickType: ClickEnum)
}