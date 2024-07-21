package com.example.currencyconverter.choosecurrency

interface ChooseCurrencyRouter {
    fun openResult(
        value: Double,
        currencyFrom: String,
        currencyTo: String
    )
}