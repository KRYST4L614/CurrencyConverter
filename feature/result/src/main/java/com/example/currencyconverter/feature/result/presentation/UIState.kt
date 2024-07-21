package com.example.currencyconverter.feature.result.presentation

sealed interface UIState {
    data class Content(
        val result: String
    ) : UIState

    data object Loading : UIState

    data class Error(
        val errorMessage: String
    ) : UIState
}