package com.example.examenmoviles.presentation.screens

import com.example.examenmoviles.domain.model.CountryCovid

data class HomeUiState(
    val countryList: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCountryDetail: CountryCovid? = null
)
