package com.example.examenmoviles.data.remote.api

import com.example.examenmoviles.data.remote.dto.CountryCovidDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidApi {

    @GET("{name}")
    suspend fun getCountryCovid(
        @Path("name") id: String,
    ): CountryCovidDto
}
