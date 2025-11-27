package com.example.examenmoviles.data.local.model

import com.example.examenmoviles.domain.model.CountryCovid

data class CountryCovidCache(
    val countryData: CountryCovid,
    val lastUpdate: Long,
    val offset: Int,
    val totalCount: Int,
)
