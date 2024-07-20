package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyRepository
import javax.inject.Inject

class ConvertCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(from: String, to: String, value: Double): Double {
        return repository.getLatest(from, to).data.getValue(to) * value
    }
}