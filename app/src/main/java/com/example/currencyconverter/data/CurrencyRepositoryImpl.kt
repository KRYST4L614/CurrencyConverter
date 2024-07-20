package com.example.currencyconverter.data

import com.example.currencyconverter.data.mappers.CurrenciesMapper
import com.example.currencyconverter.domain.CurrencyRepository
import com.example.currencyconverter.domain.entities.Currencies
import com.example.currencyconverter.domain.entities.CurrenciesLatest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val service: CurrencyService,
    private val mapper: CurrenciesMapper
) : CurrencyRepository {
    override suspend fun getCurrencies(): Currencies = withContext(dispatcher) {
        mapper.fromCurrenciesNetworkEntity(service.getCurrencies().body()!!)
    }

    override suspend fun getLatest(
        baseCurrency: String,
        currency: String
    ): CurrenciesLatest = withContext(dispatcher) {
        service.getLatest(baseCurrency, currency).body()!!
    }
}