package com.example.currencyconverter.shared.currency.data

import com.example.currencyconverter.shared.currency.domain.CurrencyRepository
import com.example.currencyconverter.shared.currency.domain.entities.LatestCurrencies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val service: CurrencyService,
) : CurrencyRepository {
    override suspend fun getLatestCurrencies(
        baseCurrency: String,
        currency: String
    ): LatestCurrencies = withContext(dispatcher) {
        service.getLatestCurrencies(baseCurrency, currency).body()!!
    }
}