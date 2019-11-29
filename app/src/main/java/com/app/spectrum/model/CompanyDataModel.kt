package com.app.spectrum.model

/**
 * Created by amresh on 29/11/2019
 */
data class CompanyDataModel(
    var companyName: String,
    var companyDesc: String,
    var companyURL: String,
    var memberList: List<MemberDataModel>

)