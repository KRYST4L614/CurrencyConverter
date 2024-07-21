package com.example.currencyconverter.shared.currency.data

import com.example.currencyconverter.shared.currency.domain.entities.LatestCurrencies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("latest")
    suspend fun getLatestCurrencies(
        @Query("base_currency") baseCurrency: String,
        @Query("currencies") currency: String
    ): Response<LatestCurrencies>
}