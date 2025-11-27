package com.example.examenmoviles.domain.model

data class CountryCovid(
    val country: String,
    val region: String,
    val cases: List<DailyInfo>
)

data class DailyInfo(
    val date: String,
    val total: Int,
    val new: Int
)