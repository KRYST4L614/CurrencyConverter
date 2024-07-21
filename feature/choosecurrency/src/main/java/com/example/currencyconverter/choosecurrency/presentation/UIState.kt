package com.example.currencyconverter.choosecurrency.presentation

sealed interface UIState {
    data class Content(
        val currencies: List<String>,
        val amountError: String? = null,
    ) : UIState

    data object Loading : UIState

    data class Error(
        val errorMessage: String
    ) : UIState
}