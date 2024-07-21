package com.example.currencyconverter.feature.result.domain

import com.example.currencyconverter.shared.currency.domain.CurrencyRepository
import javax.inject.Inject

class ConvertCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(from: String, to: String, value: Double): Double {
        return repository.getLatestCurrencies(from, to).data.getValue(to) * value
    }
}