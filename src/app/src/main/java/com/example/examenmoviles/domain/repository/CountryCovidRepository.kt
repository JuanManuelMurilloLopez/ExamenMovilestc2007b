package com.example.examenmoviles.domain.repository

import com.example.examenmoviles.domain.model.CountryCovid

interface CountryCovidRepository {
    suspend fun getCountrybyName(name: String): CountryCovid
}
