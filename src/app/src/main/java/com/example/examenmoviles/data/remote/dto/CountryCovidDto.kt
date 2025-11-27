package com.example.examenmoviles.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryCovidDto(
    @SerializedName("country") val country: String,
    @SerializedName("region") val region: String,
    @SerializedName("cases") val cases: Map<String, CaseInfoDto>
) {

}

data class CaseInfoDto(
    val total: Int,
    val new: Int
)
