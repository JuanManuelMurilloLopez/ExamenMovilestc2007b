package com.example.examenmoviles.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.domain.model.DailyInfo
import com.example.examenmoviles.domain.repository.CountryCovidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val repository: CountryCovidRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryDetailUiState())
    val uiState: StateFlow<CountryDetailUiState> = _uiState

    fun loadCountryDetail(country: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val result = repository.getCountrybyName(country)

                val casesMap = result.cases.associate { daily ->
                    daily.date to DailyInfo(total = daily.total, new = daily.new, date = daily.date)
                }
                val filtered = result.cases
                    .filter { it.total > 0 || it.new > 0 }
                    .map { it.date }

                _uiState.value = _uiState.value.copy(
                    country = result.country,
                    cases = casesMap,
                    filteredDates = filtered,
                    currentIndex = filtered.lastIndex,
                    isLoading = false
                )

            } catch (ex: Exception) {
                _uiState.value = CountryDetailUiState(
                    country = country,
                    isLoading = false,
                    error = ex.message
                )
            }
        }
    }

    fun nextDate() {
        val state = _uiState.value
        if (state.hasNext) {
            _uiState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
    }

    fun previousDate() {
        val state = _uiState.value
        if (state.hasPrevious) {
            _uiState.value = state.copy(currentIndex = state.currentIndex - 1)
        }
    }
}
