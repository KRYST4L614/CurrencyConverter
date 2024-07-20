package com.example.currencyconverter.data.mappers

import com.example.currencyconverter.data.entity.CurrenciesNetworkEntity
import com.example.currencyconverter.data.entity.CurrencyNetworkEntity
import com.example.currencyconverter.domain.entities.Currencies
import com.example.currencyconverter.domain.entities.Currency
import javax.inject.Inject

interface CurrenciesMapper {
    fun fromCurrenciesNetworkEntity(currenciesNetworkEntity: CurrenciesNetworkEntity): Currencies
}

class CurrenciesMapperImpl @Inject constructor(): CurrenciesMapper {
    override fun fromCurrenciesNetworkEntity(currenciesNetworkEntity: CurrenciesNetworkEntity): Currencies {
        return Currencies(currenciesNetworkEntity.data.mapValues {
            fromCurrencyNetworkEntity(it.value)
        })
    }

    private fun fromCurrencyNetworkEntity(currencyNetworkEntity: CurrencyNetworkEntity): Currency {
        with(currencyNetworkEntity) {
            return Currency(
                symbol, name, symbolNative, decimalDigits, rounding, code, namePlural, type
            )
        }
    }
}