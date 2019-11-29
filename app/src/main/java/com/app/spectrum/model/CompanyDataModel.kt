package com.app.spectrum.model

/**
 * Created by amresh on 29/11/2019
 */
data class CompanyDataModel(
    var _id: String,
    var company: String,
    var about: String,
    var website: String,
    var logo: String,
    var members: List<MemberDataModel>
)