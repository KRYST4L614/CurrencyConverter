package com.example.currencyconverter.choosecurrency.presentation

sealed interface UIState {
    data class Content(
        val currencies: List<String>,
        val fromCurrencyPos: Int,
        val toCurrencyPos: Int,
        val amountError: String? = null,
    ) : UIState

    data object Loading : UIState

    data class Error(
        val errorMessage: String
    ) : UIState
}