package com.example.currencyconverter.domain.entities

data class Currency(
    val symbol: String,
    val name: String,
    val symbolNative: String,
    val decimalDigits: Int,
    val rounding: Int,
    val code: String,
    val namePlural: String,
    val type: String
)