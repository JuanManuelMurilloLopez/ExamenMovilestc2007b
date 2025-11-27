package com.example.examenmoviles.data.mapper

import com.example.examenmoviles.data.remote.dto.CountryCovidDto
import com.example.examenmoviles.domain.model.CountryCovid
import com.example.examenmoviles.domain.model.DailyInfo

fun CountryCovidDto.toDomain(): CountryCovid {
    val caseList = cases.map { (date, info) ->
        DailyInfo(
            date = date,
            total = info.total,
            new = info.new
        )
    }.sortedBy { it.date }

    return CountryCovid(
        country = country,
        region = region,
        cases = caseList
    )
}