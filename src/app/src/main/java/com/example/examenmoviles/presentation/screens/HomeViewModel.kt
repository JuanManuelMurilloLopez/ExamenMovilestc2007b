package com.example.examenmoviles.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenmoviles.presentation.screens.HomeUiState
import com.example.examenmoviles.domain.usecase.GetCountryCovidUseCase
import com.example.examenmoviles.data.mockCountryList
import com.example.examenmoviles.presentation.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountryCovidUseCase: GetCountryCovidUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMockCountries()
    }

    private fun loadMockCountries() {
        _uiState.update {
            it.copy(
                countryList = mockCountryList,
                isLoading = false
            )
        }
    }

    fun loadCountryDetail(name: String) {
        viewModelScope.launch {
            getCountryCovidUseCase(name).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading ->
                            state.copy(isLoading = true)

                        is Result.Success ->
                            state.copy(
                                selectedCountryDetail = result.data,
                                isLoading = false,
                                error = null
                            )

                        is Result.Error ->
                            state.copy(
                                error = result.exception.message,
                                isLoading = false
                            )
                    }
                }
            }
        }
    }
}

