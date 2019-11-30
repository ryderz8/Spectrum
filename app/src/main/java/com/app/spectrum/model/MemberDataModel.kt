package com.app.spectrum.model

/**
 * Created by amresh on 29/11/2019
 */
data class MemberDataModel(
    var _id: String,
    var name: Name,
    var age: Int,
    var email: String,
    var phone: String
)

data class Name(
    var first: String,
    var last: String
)