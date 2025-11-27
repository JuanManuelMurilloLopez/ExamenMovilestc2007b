package com.example.examenmoviles.domain.usecase

import com.example.examenmoviles.domain.model.CountryCovid
import com.example.examenmoviles.domain.repository.CountryCovidRepository
import com.example.examenmoviles.presentation.common.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonUseCase
@Inject
constructor(
    private val repository: CountryCovidRepository,
) {
    operator fun invoke(name: String): Flow<Result<CountryCovid>> =
        flow {
            try {
                emit(Result.Loading)
                val pokemon = repository.getCountrybyName(name)
                emit(Result.Success(pokemon))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}
