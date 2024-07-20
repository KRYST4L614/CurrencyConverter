package com.example.currencyconverter.presentation.mainfragment

sealed interface MainState {
    data class Content(
        val currencies: List<String>,
        val fromCurrencyPos: Int,
        val toCurrencyPos: Int,
        val amountError: String? = null,
    ) : MainState

    data object Loading : MainState

    data class Error(
        val errorMessage: String
    ) : MainState
}