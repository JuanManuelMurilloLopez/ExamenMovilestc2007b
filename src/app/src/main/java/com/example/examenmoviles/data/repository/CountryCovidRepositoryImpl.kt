package com.example.examenmoviles.data.repository

import com.example.examenmoviles.data.local.preferences.CountryCovidPreferences
import com.example.examenmoviles.data.mapper.toDomain
import com.example.examenmoviles.data.remote.api.CovidApi
import com.example.examenmoviles.domain.model.CountryCovid
import com.example.examenmoviles.domain.repository.CountryCovidRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.let

@Singleton
class CountryCovidRepositoryImpl
@Inject
constructor(
    private val api: CovidApi,
    private val preferences: CountryCovidPreferences,
) : CountryCovidRepository {

    override suspend fun getCountrybyName(name: String): CountryCovid {
        // Intentar obtener del caché primero
        preferences.getCountryCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                cache.countryData.find { it.country == name }?.let { return it }
            }
        }

        return try {
            // Si no hay caché o expiró, obtener de la API
            api.getCountryCovid(name).toDomain()
        } catch (e: Exception) {
            // Si hay error, intentar buscar en el caché aunque haya expirado
            preferences.getCountryCache()?.let { cache ->
                cache.countryData.find { it.country == name }
            } ?: throw e
        }
    }
}
