package com.example.examenmoviles.di

import android.content.Context
import com.example.examenmoviles.data.local.preferences.CountryCovidPreferences
import com.example.examenmoviles.data.remote.api.CovidApi
import com.example.examenmoviles.data.repository.CountryCovidRepositoryImpl
import com.example.examenmoviles.domain.repository.CountryCovidRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import java.util.prefs.Preferences


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Api-Key", "cjp3inHUoHC7M5I2vknvLA==vbqm2e1QlsTRkO4y")
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .client(client)
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