package com.example.examenmoviles.data.remote.api

import com.example.examenmoviles.data.remote.dto.CountryCovidDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidApi {

    @GET("covid19")
    suspend fun getCountryCovid(
        @Query("country") country: String
    ): List<CountryCovidDto>
}
