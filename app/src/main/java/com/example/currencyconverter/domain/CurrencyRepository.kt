package com.example.currencyconverter.domain

import com.example.currencyconverter.domain.entities.Currencies
import com.example.currencyconverter.domain.entities.CurrenciesLatest

interface CurrencyRepository {
    suspend fun getCurrencies(): Currencies
    suspend fun getLatest(baseCurrency: String, currency: String): CurrenciesLatest
}