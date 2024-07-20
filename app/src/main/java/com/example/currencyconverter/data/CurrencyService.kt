package com.example.currencyconverter.data

import com.example.currencyconverter.data.entity.CurrenciesNetworkEntity
import com.example.currencyconverter.domain.entities.CurrenciesLatest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("currencies")
    suspend fun getCurrencies(): Response<CurrenciesNetworkEntity>

    @GET("latest")
    suspend fun getLatest(
        @Query("base_currency") baseCurrency: String,
        @Query("currencies") currency: String
    ): Response<CurrenciesLatest>
}