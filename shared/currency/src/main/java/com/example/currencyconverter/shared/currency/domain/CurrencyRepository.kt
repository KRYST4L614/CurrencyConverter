package com.example.currencyconverter.shared.currency.domain

import com.example.currencyconverter.shared.currency.domain.entities.LatestCurrencies

interface CurrencyRepository {
    suspend fun getLatestCurrencies(
        baseCurrency: String = "",
        currency: String = ""
    ): LatestCurrencies
}