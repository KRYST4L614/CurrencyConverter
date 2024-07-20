package com.example.currencyconverter.domain.usecases

import com.example.currencyconverter.domain.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke() = repository.getCurrencies()
}