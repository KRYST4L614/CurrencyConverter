package com.example.currencyconverter.presentation.resultfragment

sealed interface ResultState {
    data class Content(
        val result: String
    ) : ResultState

    data object Loading : ResultState

    data class Error(
        val errorMessage: String
    ) : ResultState
}