package com.example.currencyconverter.choosecurrency.domain

import com.example.currencyconverter.shared.currency.domain.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke() = repository.getLatestCurrencies()
}