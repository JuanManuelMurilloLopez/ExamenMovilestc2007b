package com.example.examenmoviles.presentation.screens

import com.example.examenmoviles.domain.model.DailyInfo

data class CountryDetailUiState(
    val country: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val cases: Map<String, DailyInfo> = emptyMap(),

    val filteredDates: List<String> = emptyList(),
    val currentIndex: Int = 0
) {
    val currentDate: String?
        get() = filteredDates.getOrNull(currentIndex)

    val currentInfo: DailyInfo?
        get() = currentDate?.let { cases[it] }

    val hasPrevious: Boolean
        get() = currentIndex > 0

    val hasNext: Boolean
        get() = currentIndex < filteredDates.lastIndex
}
