package com.example.examenmoviles.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    viewModel: CountryDetailViewModel,
    countryName: String,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(countryName) {
        viewModel.loadCountryDetail(countryName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(countryName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            when {
                state.isLoading -> CircularProgressIndicator()

                state.error != null -> Text("Error: ${state.error}")

                state.filteredDates.isEmpty() -> Text("No existen datos para este país")

                else -> {
                    CountryDetailContent(
                        date = state.currentDate ?: "",
                        info = state.currentInfo,
                        hasPrev = state.hasPrevious,
                        hasNext = state.hasNext,
                        onPrev = { viewModel.previousDate() },
                        onNext = { viewModel.nextDate() }
                    )
                }
            }
        }
    }
}
