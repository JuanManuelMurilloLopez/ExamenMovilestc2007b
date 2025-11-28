package com.example.examenmoviles.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.examenmoviles.domain.model.CountryCovid
import com.example.examenmoviles.presentation.screens.HomeViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCountryClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Covid Stats by Country") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {

            val filteredCountryList = uiState.countryList.filter { country ->
                country.contains(searchQuery, ignoreCase = true)
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar país") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            CountryListContent(
                countryList = filteredCountryList,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onCountryClick = onCountryClick
            )
        }
    }
}

@Composable
fun CountryListContent(
    countryList: List<String>,
    isLoading: Boolean,
    error: String?,
    onCountryClick: (String) -> Unit
) {
    when {
        isLoading -> {
            Text("Cargando...", modifier = Modifier.padding(16.dp))
        }

        error != null -> {
            Text("Error: $error", modifier = Modifier.padding(16.dp))
        }

        else -> {
            LazyColumn {
                items(countryList) { country ->
                    CountryItem(country = country, onClick = { onCountryClick(country) })
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: String, onClick: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = country,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


