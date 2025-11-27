package com.example.examenmoviles.di

import android.content.Context
import com.example.examenmoviles.data.local.preferences.CountryCovidPreferences
import com.example.examenmoviles.data.remote.api.CovidApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.prefs.Preferences


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/v1/covid19?country=")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideCovidApi(retrofit: Retrofit): CovidApi = retrofit.create(CovidApi::class.java)

    @Provides
    @Singleton
    fun provideCovidPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): CountryCovidPreferences = CountryCovidPreferences(context, gson)

    @Provides
    @Singleton
    fun provideCountryCovidRepository(
        api: CovidApi,
        preferences: CountryCovidPreferences,
    ): CountryCovidRepository = CountryCovidRepositoryImpl(api, preferences)
}